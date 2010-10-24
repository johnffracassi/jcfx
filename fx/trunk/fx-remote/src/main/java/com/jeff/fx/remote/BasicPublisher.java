package com.jeff.fx.remote;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import com.sun.messaging.TopicConnectionFactory;

public abstract class BasicPublisher {

	private TopicConnectionFactory connectionFactory;
	private TopicConnection connection;
	private TopicSession session;
	private TopicPublisher publisher;
	private Topic topic;
	
	public abstract String getTopicName();

    @PostConstruct
    private void init() 
    {
        try 
        {
            connectionFactory = new TopicConnectionFactory();
            connection = connectionFactory.createTopicConnection();
            connection.start();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(getTopicName());
			publisher = session.createPublisher(topic);
        } 
        catch(Exception e) 
        {
            System.out.println("Exception, could not create queue connection or session: " + e.getMessage() + "");
            e.printStackTrace();
        }
    }
  
    protected void publish(Map<String,Object> map)  
    {
		try 
		{
			MapMessage msg = session.createMapMessage();
			for(String key : map.keySet())
			{
				msg.setString(key, String.valueOf(map.get(key)));
			}
			publisher.send(msg, DeliveryMode.PERSISTENT, 4, 0);
			System.out.println("message published");
        } 
		catch(JMSException e) 
		{
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void cleanup() 
    {
        try 
        {
            System.out.println("Closing queue session and queue connection");
            session.close();
            connection.close();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
