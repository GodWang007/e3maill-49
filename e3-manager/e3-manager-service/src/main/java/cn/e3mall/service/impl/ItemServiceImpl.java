package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.filter.function.regexMatchFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.thrift.ThriftUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

/**
 * 商品管理
 * @author 11734
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	@Value("${item.info.expire}")
	private Integer itemInfoExpire;
	/**
	 * 根据ID查询
	 */
	public TbItem getItemById(long itemId) {
		//查询缓存
		try {
			String json = jedisClient.get("ITEM_INFO"+itemId+":BASE");
			if (StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, 	TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果缓存中没有   查询数据库
		
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		try {
			//把结果添加到缓存
			jedisClient.set("ITEM_INFO"+itemId+":BASE",JsonUtils.objectToJson(tbItem));
			//设置过期时间
			jedisClient.expire("ITEM_INFO"+itemId+":BASE", itemInfoExpire);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}
	/**
	 * 查询列表
	 */
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		//获取总记录数
		
		long total = pageInfo.getTotal();
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setTotal(total);
		result.setRows(list);
		//返回结果
		return result;
	}
	/**
	 * 添加商品
	 */
	public E3Result addItem(TbItem item, String desc) {
		//生成商品的ID
		final long itemId = IDUtils.genItemId();
		
		//补全item的属性
		item.setId(itemId);
		//1:正常  2:下架  3:删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到商品列表
		itemMapper.insert(item);
		//创建一个TBItemDesc对象
		TbItemDesc itemDesc=new TbItemDesc();
		//补全属性
		itemDesc.setCreated(new Date());
		itemDesc.setItemId(itemId);
		itemDesc.setUpdated(new Date());
		itemDesc.setItemDesc(desc);
		//插入到商品描述表
		itemDescMapper.insert(itemDesc);
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				
				return session.createTextMessage(itemId+"");
			}
		});
		//返回OK
		return E3Result.ok();
	}
	/**
	 * 根据商品id取商品描述
	 */
	public TbItemDesc getItemDescById(long itemId) {
		//查询缓存
		try {
			String json = jedisClient.get("ITEM_INFO"+itemId+":DESC");
			if (StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, 	TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try {
			//把结果添加到缓存
			jedisClient.set("ITEM_INFO"+itemId+":DESC",JsonUtils.objectToJson(tbItemDesc));
			//设置过期时间
			jedisClient.expire("ITEM_INFO"+itemId+":DESC", itemInfoExpire);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItemDesc;
	}
	
}
