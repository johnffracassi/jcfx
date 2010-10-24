package com.jeff.fx.remote;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.remote.view.NodeType;
import com.jeff.fx.remote.view.RemoteNode;

@Component
public class AvailabilityConsumer extends BasicConsumer<AvailabilityListener> {

	@Override
	public String getTopicName()
	{
		return "availability";
	}

	@Autowired
	public void setListeners(List<AvailabilityListener> listeners)
	{
		for(AvailabilityListener listener : listeners)
		{
			addListener(listener);
		}
	}
	
	@Override
	protected void notifyListener(AvailabilityListener listener, Message message) 
	{
		if(message instanceof MapMessage)
		{
			MapMessage msg = (MapMessage)message;
			
			try
			{
				String id = msg.getString("node-id");
				NodeType nodeType = NodeType.valueOf(msg.getString("node-type"));
				
				if(nodeType == NodeType.Server)
				{
					listener.newServer(new RemoteNode(id, NodeType.Server));
				}
				else if(nodeType == NodeType.Client)
				{
					listener.newClient(new RemoteNode(id, NodeType.Client));
				}
			}
			catch(JMSException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
