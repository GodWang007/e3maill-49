package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	/**
	 * 获取内容分类列表
	 * 参数：long parentId
	 * 返回值：List<EasyUITreeNode>
	 */
	public List<EasyUITreeNode> ContentCategoryList(long parentId);
	//参数   long parentId     string name
	//返回值  E3Result
	//添加内容分类
	public E3Result addContentCategory(long parentId,String name);
	
}
