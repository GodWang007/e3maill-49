package cn.e3mall.jedis;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	@Test
	public void testJedis() throws Exception{
		Jedis jedis=new Jedis("192.168.25.128",6379);
		jedis.set("test", "100");
		String string = jedis.get("test");
		System.out.println(string);
		jedis.close();
		
	}
	@Test
	public void testJedisPool() throws Exception{
		JedisPool jedisPool=new JedisPool("192.168.25.128",6379);
		Jedis jedis=jedisPool.getResource();
		
		String string = jedis.get("test");
		System.out.println(string);
		//每次使用完毕之后都要关闭
		jedis.close();
		jedisPool.close();
		
	}
	@Test
	public void testJedisCluster() throws Exception{
		//一个构造参数set,集合中有很多HostAndPort对象
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		//创建一个jedisCluster对象
		JedisCluster jedisCluster=new JedisCluster(nodes);
				
		//直接使用JedisCluster操作jedis
		String string = jedisCluster.get("a");
		System.out.println(string);
		//系统结束之前关闭jediscluster对象
		jedisCluster.close();
		
	}
	@Test
	public void testJedisclient() throws Exception{
	
		//初始化spring容器
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//从容器中获取jedisclient对象
		JedisClient client = applicationContext.getBean(JedisClient.class);
		//直接使用jedisclient对象
		String string = client.get("a");
		System.out.println(string);
		
		
	}
}
