package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;
/**
 * 内容分类管理
 * @author 11734
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	/**
	 * 展示分类内容
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> ContentCategoryList(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<EasyUITreeNode> list = contentCategoryService.ContentCategoryList(parentId);
		return list;
		
	}
	/**
	 * 内容分类:新增节点
	 */
	@RequestMapping("/create")
	@ResponseBody
	public E3Result addContentCategory(Long parentId,String name){
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
		
	}
	
}
