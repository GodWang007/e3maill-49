package cn.e3mall.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
/**
 * 接收Activemq发送的添加商品的消息,把商品同步到索引库
 * @author 11734
 *
 */
public class ItemAddMessageListener implements MessageListener {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	public void onMessage(Message message) {
		try {
			// a）从Message中取消息的内容。
			TextMessage textMessage = (TextMessage) message;
			Long itemId = null;
			itemId = Long.parseLong(textMessage.getText());
			// b）根据商品id查询商品信息。需要自己写mapper
			//等待事务提交
			Thread.sleep(1000);
			SearchItem searchItem = itemMapper.getItemById(itemId);
			// c）创建一个文档对象
			SolrInputDocument document = new SolrInputDocument();
			// d）向文档对象中添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			// e）把文档对象写入索引库
			solrServer.add(document);
			// f）提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
