package cn.e3mall.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
	public TbItem getItemById(long itemid);
	EasyUIDataGridResult getItemList(int page, int rows);
	//参数TbItem,String desc
	//返回值 E3Result
	E3Result addItem(TbItem item,String desc);
	//根据商品id取商品描述
	TbItemDesc getItemDescById(long itemId);
}
