package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 商品详情页展示
 * @author 11734
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable long itemId,Model model){
		// 1、根据商品id查询商品信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item=new Item(tbItem);
		// 2、根据商品id查询商品描述
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		
		// 3、把数据传递给jsp
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",tbItemDesc);
		// 4、在页面展示图片列表，把字符串拆分。
		// 可以继承TbItem添加一个getImages方法
		//返回逻辑视图
		return "item";
		
	}
	
}
