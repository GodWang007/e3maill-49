package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
/**
 * 商品查询服务实现类
 * @author 11734
 *
 */
@Service
public class SearchServiceImpl implements SearchService{
	
	// 参数：
	// keyword：查询条件
	// page：分页条件
	// rows：每页显示的记录数
	// 返回值：
	// SearchResult
	
	@Autowired
	private SearchDao searchDao;
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		// 业务逻辑:
		// 1）接收参数
		// 2）创建一个查询对象。
		SolrQuery solrQuery=new SolrQuery();
		// 3）设置查询条件
		solrQuery.setQuery(keyword);
		//分页条件
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		//设置迷人搜索域
		solrQuery.set("df","item_title");
		//开启高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		// 4）调用dao执行查询
		SearchResult searchResult = searchDao.search(solrQuery);
		// 5）计算总页数
		long recourdCount = searchResult.getRecourdCount();
		long pageCount=recourdCount / rows;
		if (recourdCount%rows>0) {
			pageCount++;
		}
		searchResult.setTotalPages(pageCount);
		// 6）返回结果
		
		
		return searchResult;
	}

}
