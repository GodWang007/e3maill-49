package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import javassist.compiler.ast.NewExpr;

/**
 * 商品查询表现层
 * @author 11734
 *
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	@Value("${search.result.rows}")
	private Integer ROWS;
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		//解决乱码问题
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		
		//调用服务查询商品列表
		SearchResult searchResult = searchService.search(keyword, page, ROWS);
		//把结果传递给jsp
		//查询参数回显
		model.addAttribute("itemList",searchResult.getItemList());
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecourdCount());
		//返回逻辑视图
		return "search";
		
	}
}
