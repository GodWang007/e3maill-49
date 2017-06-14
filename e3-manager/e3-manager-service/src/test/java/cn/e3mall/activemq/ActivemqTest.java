package cn.e3mall.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActivemqTest {

	//点到点形式的发送消息
	@Test
	public void testQueueProducer() throws Exception {
		//1、创建一个ConnectionFactory对象。构造参数需要指定Activemq服务的ip及端口号。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.128.25.128:61616");
		//2、使用ConnectionFactory对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		//3、开启连接，调用connection对象的start方法。
		connection.start();
		//4、使用Connection对象创建一个Session对象。
		//参数1：是否开启事务，Activemq的事务。如果开启事务，commit后消息会统一发送，rollback消息全部取消。
		//参数2：如果开启事务，第二个参数无意义。如果不开启事务，消息应答模式：手动应答或者自动应答。通常自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5、使用Session对象创建一个Destination对象，创建一Queue对象。
		//参数就是队列的名称
		Queue queue = session.createQueue("test-queue");
		//6、使用Session对象创建一个Producer对象，需要指定一个Destination对象。
		MessageProducer producer = session.createProducer(queue);
		//7、创建一个Message对象，使用TextMessage对象。
		/*TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello activemq");*/
		TextMessage textMessage = session.createTextMessage("hello activemq");
		//8、发送消息
		producer.send(textMessage);
		//9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testQueueConsumer() throws Exception {
		//1、创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.128.25.128:61616");
		//2、使用ConnectionFactory创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//3、开启连接
		connection.start();
		//4、使用connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5、使用Session创建一个Destination对象,Queue
		Queue queue = session.createQueue("test-queue");
		//6、使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(queue);
		//7、设置消息的监听器。
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					//8、在监听器中接收消息，并打印
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		//等待接收消息
		System.out.println("Queue消费者已经启动。。。。");
		System.in.read();
		System.out.println("Queue消费者正在关闭。。。。");
		//9、关闭资源
		consumer.close();
		session.close();
		connection.close();
		System.out.println("Queue消费者已经关闭");
	}
	
	@Test
	public void testTopicProducer() throws Exception {
		//创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.128.25.128:61616");
		//创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination对象。创建一个Topic
		Topic topic = session.createTopic("test-topic");
		//创建一个Producer对象
		MessageProducer producer = session.createProducer(topic);
		//创建一个消息对象
		TextMessage textMessage = session.createTextMessage("topic message");
		//发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicConsumer() throws Exception {
		//1、创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.128.25.128:61616");
		//2、使用ConnectionFactory创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//3、开启连接
		connection.start();
		//4、使用connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5、使用Session创建一个Destination对象,Topic
		Topic topic = session.createTopic("test-topic");
		//6、使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(topic);
		//7、设置消息的监听器。
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					//8、在监听器中接收消息，并打印
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		//等待接收消息
		System.out.println("Queue消费者3已经启动。。。。");
		System.in.read();
		System.out.println("Queue消费者3正在关闭。。。。");
		//9、关闭资源
		consumer.close();
		session.close();
		connection.close();
		System.out.println("Queue消费者3已经关闭");
	}
}
