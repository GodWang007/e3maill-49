package cn.e3mall.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemAddMessageListener implements MessageListener {
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("html.gen.path")
	private String  htmlGenPath;
	public void onMessage(Message message) {
		try {
		// 1、在e3-item-web工程中订阅商品添加消息。
		// 2、从消息中取商品id
		TextMessage textMessage=(TextMessage) message;
		Long itemId=new Long(textMessage.getText());
		
		Thread.sleep(1000);
		// 3、根据商品id 查询商品信息
		TbItem tbItem = itemService.getItemById(itemId);
		// 商品基本信息
		Item item=new Item(tbItem);
		// 商品描述
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		// 4、生成静态页面，需要freemarker的模板。对应商品详情页面创建一个模板。
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		//创建一个数据集
		Map data=new HashMap<>();
		data.put("item", item);
		data.put("itemDesc", tbItemDesc);
		
		// 5、指定静态页面输出的目录，及文件名。
		Writer out=new FileWriter(htmlGenPath+itemId+".html");
		// 7、生成文件
		template.process(data, out);
		// 8、关闭流
		out.close();
		// 9、配置nginx访问静态页面。
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}

}
