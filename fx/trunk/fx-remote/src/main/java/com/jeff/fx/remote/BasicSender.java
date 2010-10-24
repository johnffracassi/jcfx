package com.jeff.fx.remote;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import com.sun.messaging.QueueConnectionFactory;


public abstract class BasicSender {

	private QueueConnectionFactory connectionFactory;
	private QueueConnection connection;
	private QueueSession session;
	private QueueSender sender;
	private Queue queue;
	
    public abstract String getQueueName();
    
    @PostConstruct
    private void init() 
    {
        try 
        {
            connectionFactory = new QueueConnectionFactory();
            connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(getQueueName());
			sender = session.createSender(queue);
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
			sender.send(msg, DeliveryMode.PERSISTENT, 4, 0);
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
