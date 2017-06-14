package cn.e3mall.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.e3mall.common.pojo.SearchResult;

/**
 * 查询索引库dao
 * @author 11734
 *
 */
public interface SearchDao {
	SearchResult search(SolrQuery solrQuery) throws Exception;
}
