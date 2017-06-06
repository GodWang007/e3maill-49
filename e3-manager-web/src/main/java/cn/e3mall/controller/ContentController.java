package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	/**
	 * 增加内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content){
		E3Result e3Result = contentService.addContent(content);
		return e3Result;
	}
	/**
	 *根据分类id 分页
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}

}
