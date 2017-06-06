package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	/**
	 *内容分类管理
	 */
	public List<EasyUITreeNode> ContentCategoryList(long parentId) {
		// 1、取查询参数id，parentId
		// 2、根据parentId查询tb_content_category，查询子节点列表。
		TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = tbContentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		// 3、得到List<TbContentCategory>
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
		
		// 4、把列表转换成List<EasyUITreeNode>
		List<EasyUITreeNode> resultlist=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode easyUITreeNode=new EasyUITreeNode();
			easyUITreeNode.setId(tbContentCategory.getId());
			easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			easyUITreeNode.setText(tbContentCategory.getName());
			//添加到列表
			resultlist.add(easyUITreeNode);
		}
		

		return resultlist;
	}
	/**
	 * 新增节点
	 * 参数：parentId
		 	  name
		返回值：E3Result，其中包含id
		
	 */
	public E3Result addContentCategory(long parentId, String name) {
		// 业务逻辑：
		// 1、创建一个TbContentCategory对象
		TbContentCategory tbContentCategory=new TbContentCategory();
		// 2、补全属性
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		tbContentCategory.setSortOrder(1);
		//状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// 3、插入记录
		tbContentCategoryMapper.insert(tbContentCategory);
		// 4、判断父节点的isparent属性，如果不是true应该改为true。
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父节点
			tbContentCategoryMapper.updateByPrimaryKey(parentNode);
			
			
		}
		// 5、返回结果，包含id
		return E3Result.ok(tbContentCategory);
	}

}
