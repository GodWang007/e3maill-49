package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 展示首页
 * @author 11734
 *
 */
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	@Value("${index.slider.cid}")
	private long indexSliderCid;
	@RequestMapping("/index")
	public String showIndex(Model model){
		//根据分类cid查询内容列表
		List<TbContent> ad1List =
				contentService.getContentListByCid(indexSliderCid);
		//把结果传递给jsp
		model.addAttribute("ad1List",ad1List);
		//返回逻辑视图
		
		return "index";
	}
	
	
}
