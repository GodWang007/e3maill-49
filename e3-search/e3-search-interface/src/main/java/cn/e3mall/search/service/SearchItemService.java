package cn.e3mall.search.service;

import cn.e3mall.common.pojo.E3Result;

public interface SearchItemService {
	/**
	 * 把商品数据导入到索引库
	 * @return
	 */
	E3Result importItems() throws Exception;
}
