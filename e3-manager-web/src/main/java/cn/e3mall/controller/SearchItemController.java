package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.service.SearchItemService;
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result ImportItemList(){
		try {
			E3Result e3Result = searchItemService.importItems();
			return e3Result;
		} catch (Exception e) {
			
			e.printStackTrace();
			return E3Result.build(500, e.getMessage());
		}
		
	}
}
