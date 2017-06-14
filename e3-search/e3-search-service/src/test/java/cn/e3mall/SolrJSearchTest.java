package cn.e3mall;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrJSearchTest {
	@Test
	public void testSearch () throws Exception{
		//创建一个SolrServer对象,应该使用HttpSolrServer
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
		//创建一个SolrjQuery对象
		SolrQuery solrQuery=new SolrQuery();
		
		//设置各种查询参数
		solrQuery.setQuery("手机");
		solrQuery.setStart(0);
		solrQuery.setRows(5);
		solrQuery.set("df", "item_title");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		//执行查询得到查询结果
		QueryResponse response = solrServer.query(solrQuery);
		
		//从查询结果中取查询结果的总记录数
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数:"+solrDocumentList.getNumFound());
		//取高亮结果
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//取商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			//取高亮结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if (list!=null&&list.size()>0) {
				title=list.get(0);
						
			}else{
				title=solrDocument.get("item_title").toString();
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_price"));
		}
		
		
	}
}
