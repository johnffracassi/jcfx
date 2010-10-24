package com.jeff.fx.remote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

import com.sun.messaging.QueueConnectionFactory;

public abstract class BasicReceiver<L extends BasicListener> {

	private List<L> listeners = new ArrayList<L>();
	
	private QueueConnectionFactory connectionFactory;
	private QueueConnection connection;
	private QueueSession session;
	private QueueReceiver receiver;
	private Queue queue;

	public abstract String getQueueName();
	
    @PostConstruct
    private void run()
    {
		try  
		{
            connectionFactory = new QueueConnectionFactory();
            connection = connectionFactory.createQueueConnection();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(getQueueName());
			connection.start();
			
			receiver = session.createReceiver(queue);
			receiver.setMessageListener(new MessageListener() {
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
}
