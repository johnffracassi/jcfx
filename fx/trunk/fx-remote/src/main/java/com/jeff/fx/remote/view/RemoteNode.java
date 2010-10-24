package com.jeff.fx.remote.view;

public class RemoteNode {

	private String id;
	private String description;
	private String address;
	private String location;
	private NodeType type;
	private NodeState state;

	public RemoteNode(String id, NodeType type)
	{
		this.id = id;
		this.type = type;
		this.state = NodeState.Idle;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

}
