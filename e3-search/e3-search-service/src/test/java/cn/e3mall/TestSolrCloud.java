package cn.e3mall;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

	//向索引库添加文档
	@Test
	public void addDocument() throws Exception {
		//1、创建一个SolrServer对象，需要使用CloudSolrServer
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
		//2、构造参数需要指定zookeeper的地址列表
		//3、指定要对哪个Collection进行管理需要设置DefaultCollection属性。
		cloudSolrServer.setDefaultCollection("collection2");
		//4、创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//5、向文档对象中添加域
		document.addField("id", "test01");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//6、把文档对象写入索引库
		cloudSolrServer.add(document);
		//7、提交
		cloudSolrServer.commit();
	}
}
