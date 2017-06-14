package cn.e3mall;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SortJTest {
	@Test
	public void addDocument() throws Exception{
		//创建一个solrserver对象HttpSolrServer
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument solrInputDocument=new SolrInputDocument();
		
		//向文档对象中添加域,每个文档中必须有id域,而且每个域必须在schema.xml中定义
		solrInputDocument.addField("id", "test01");
		solrInputDocument.addField("item_title", "测试商品1");
		solrInputDocument.addField("item_price", "100");
		//把文档对象写入索引库
		solrServer.add(solrInputDocument);
		//提交
		solrServer.commit();
	}
	@Test
	public void deleteDocumentById() throws Exception{
		//创建一个solrserver对象HttpSolrServer
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		solrServer.deleteById("test01");
		//提交
		solrServer.commit();
	}
	@Test
public void deleteDocumentByQuery() throws Exception{
		//创建一个solrserver对象HttpSolrServer
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		solrServer.deleteByQuery("*:*");
		//提交
		solrServer.commit();
	}
	
	
}
