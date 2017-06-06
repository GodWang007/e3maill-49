package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

public interface ContentService {
	/**
	 *根据分类id 分页
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getContentList(long categoryId,int page,int rows);
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	public E3Result addContent(TbContent content);
	/**
	 * 根据分类id查找内容列表
	 * @param cid
	 * @return
	 */
	public List<TbContent> getContentListByCid(long cid);
	
}
