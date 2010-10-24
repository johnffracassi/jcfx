package com.jeff.fx.remote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import com.sun.messaging.TopicConnectionFactory;

public class BasicConsumer<L extends BasicListener> {

	private List<L> listeners = new ArrayList<L>();
	
	private String topicName = "availability";
	private TopicConnectionFactory connectionFactory;
	private TopicConnection connection;
	private TopicSession session;
	private TopicSubscriber subscriber;
	private Topic topic;

    @PostConstruct
    private void run()
    {
		try 
		{
            connectionFactory = new TopicConnectionFactory();
            connection = connectionFactory.createTopicConnection();
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic(getTopicName());
			connection.start();
			
			subscriber = session.createSubscriber(topic);
			subscriber.setMessageListener(new MessageListener() {
				public void onMessage(Message msg) {
					messageReceived(msg);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void addListener(L listener)
    {
    	listeners.add(listener);
    }
    
    public void removeListener(L listener)
    {
    	listeners.remove(listener);
    }
    
    protected void notifyListener(L listener, Message message)
    {
    	listener.messageReceived(message);
    }
    
    protected void messageReceived(Message msg)
    {
    	for(L listener : listeners)
    	{
    		notifyListener(listener, msg);
    	}
    }
    
    @PreDestroy
    private void cleanup() 
    {
        try 
        {
        	listeners.clear();
        	listeners = null;
            session.close();
            connection.close();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
