package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.JsonUtils;
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
	@Autowired
	private JedisClient jedisClient;
	/**
	 * 新增内容
	 */
	public E3Result addContent(TbContent content) {
		//补全属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//同步 缓存
		jedisClient.hdel("CONTENT_INFO", content.getCategoryId().toString());
		
		return E3Result.ok();
	}
	/**
	 * 根据分类cid查找内容列表
	 */
	public List<TbContent> getContentListByCid(long categoryId) {
		//查询数据库之前先查询缓存
		try {
			//如果缓存中有数据直接返回
			String result = jedisClient.hget("COUNT_INFO", categoryId+"");
			if (StringUtils.isNotBlank(result)) {
				//把字符串转换成list
				List<TbContent> list = JsonUtils.jsonToList(result, TbContent.class);
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		//如果没有数据查询数据库并放到缓存
		
		//设置查询的条件
		TbContentExample contentExample=new TbContentExample();
		Criteria criteria=contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		try {
			//添加到缓存
			jedisClient.hset("CONTENT_INFO", categoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
