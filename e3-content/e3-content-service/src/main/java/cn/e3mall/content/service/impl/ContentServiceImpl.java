package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.doclets.internal.toolkit.resources.doclets;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
/**
 * 内容管理
 * @author 11734
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	/**
	 * 新增内容
	 */
	public E3Result addContent(TbContent content) {
		//补全属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		
		return E3Result.ok();
	}
	/**
	 * 根据分类cid查找内容列表
	 */
	public List<TbContent> getContentListByCid(long categoryId) {
		//设置查询的条件
		TbContentExample contentExample=new TbContentExample();
		Criteria criteria=contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		
		return list;
	}
	/**
	 *根据分类id 分页
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//根据分页id查询内容列表
		TbContentExample contentExample=new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		//取分页结果
		PageInfo<TbContent> pageInfo=new PageInfo<>(list);
		long total = pageInfo.getTotal();
		//创建一个EasyUIDataGridResult对象
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(total);
		return result;
	}

}
