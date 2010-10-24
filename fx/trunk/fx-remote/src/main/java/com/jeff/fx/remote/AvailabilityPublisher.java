package com.jeff.fx.remote;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jeff.fx.remote.view.NodeType;

@Component
public class AvailabilityPublisher extends BasicPublisher {
	
	@Override
	public String getTopicName() 
	{
		return "availability";
	}
	
	public void announceAsClient(String id)
	{
		Map<String,Object> map = buildMap(id);
		map.put("node-type", NodeType.Client);
		publish(map);
	}
	
	public void announceAsServer(String id)
	{
		Map<String,Object> map = buildMap(id);
		map.put("node-type", NodeType.Client);
		publish(map);
	}
	
	private Map<String,Object> buildMap(String id)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("node-id", id);
		map.put("description", id);
		return map;
	}
}
