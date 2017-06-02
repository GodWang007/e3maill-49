package cn.e3mall.service;

import cm.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(long itemid);
	EasyUIDataGridResult getItemList(int page, int rows);
}
