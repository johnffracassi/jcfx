package com.jeff.fx.remote.view;

import javax.annotation.PostConstruct;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.remote.AvailabilityConsumer;
import com.jeff.fx.remote.AvailabilityListener;
import com.jeff.fx.remote.AvailabilityPublisher;

@Component
public class AvailabilityController implements AvailabilityListener {

	@Autowired
	private AvailabilityConsumer consumer;
	
	@Autowired
	private AvailabilityPublisher publisher;
	
	private AvailabilityView view;
	private AvailabilityTableModel tableModel;
	private String nodeId;
	
	@PostConstruct
	private void init() {

		view = new AvailabilityView();
		tableModel = new AvailabilityTableModel();
		view.setTableModel(tableModel);
	}

	public AvailabilityView getView() 
	{
		return view;
	}

	public void announceAsClient(String id)
	{
		this.nodeId = id;
		publisher.announceAsClient(id);
	}
	
	@Override
	public void newServer(RemoteNode node) 
	{
		if(!tableModel.containsNode(node))
		{
			tableModel.addNode(node);
			announceAsClient(nodeId);
		}
	}

	@Override
	public void newClient(RemoteNode node) 
	{
		if(!tableModel.containsNode(node))
		{
			tableModel.addNode(node);
			announceAsClient(nodeId);
		}
	}

	@Override
	public void messageReceived(Message message) 
	{
	}
}
