package cn.e3mall.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
/**
 * 索引库维护service
 * @author 11734
 *
 */
@Service
public class SearchItemServiceImpl implements SearchItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	/**
	 * 把商品数据导入到索引库
	 * 
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public E3Result importItems() throws Exception {
		// 1）查询商品列表
		List<SearchItem> list = itemMapper.getItemList();
		// 2）把商品列表写入索引库
		for (SearchItem searchItem : list) {
			//创建一个文档对象
			SolrInputDocument solrInputDocument=new SolrInputDocument();
			//向文档对象中添加域
			solrInputDocument.addField("id", searchItem.getId());
			solrInputDocument.addField("item_title", searchItem.getTitle());
			solrInputDocument.addField("item_sell_point", searchItem.getSell_point());
			solrInputDocument.addField("item_price", searchItem.getPrice());
			solrInputDocument.addField("item_image", searchItem.getImage());
			solrInputDocument.addField("item_category_name", searchItem.getCategory_name());
			//把文档写入索引库
			// 使用solrJ写入索引库
			solrServer.add(solrInputDocument);
		}
		//提交
		solrServer.commit();
		// 3）返回成功
		return E3Result.ok();
	}
	


}
