package cn.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestSpringActivemq {
	
		@Test
		public void testSendMessage() throws Exception {
			//初始化spring容器
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
			//从容器中获得JmsTemplate对象
			JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
			//使用JmsTemplate对象发送消息
			Destination destination = (Destination) applicationContext.getBean("queueDestination");
			jmsTemplate.send(destination, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage("hello spring activemq");
				}
			});
			
		}
}
