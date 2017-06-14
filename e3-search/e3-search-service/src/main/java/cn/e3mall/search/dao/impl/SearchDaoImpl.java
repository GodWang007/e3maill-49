package cn.e3mall.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
/**
 * 商品搜索dao
 * @author 11734
 *
 */
@Repository
public class SearchDaoImpl implements SearchDao{
	// 使用solrJ查询索引库
	// 参数：SolrQuery对象
	// 返回值：SearchResult
	@Autowired
	private SolrServer solrServer;
	@Override
	public SearchResult search(SolrQuery solrQuery) throws Exception {
		// 业务逻辑：
		// 1）执行查询、
		QueryResponse response = solrServer.query(solrQuery);
		
		// 2）取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		// 3）查询结果的总记录数
		long total = solrDocumentList.getNumFound();
		// 4）商品列表，取高亮结果
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		List<SearchItem> itemList=new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem=new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if (list!=null&&list.size()>0) {
				title=list.get(0);
				
			}else {
				title=(String) solrDocument.get("item_title");
			}
			
			searchItem.setTitle(title);
			itemList.add(searchItem);
			
			
		}
		// 5）返回结果
		SearchResult searchResult=new SearchResult();
		searchResult.setItemList(itemList);
		searchResult.setRecourdCount(total);
		
		return searchResult;
	}
	
}
