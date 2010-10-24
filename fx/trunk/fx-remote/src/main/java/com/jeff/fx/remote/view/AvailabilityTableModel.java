package com.jeff.fx.remote.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class AvailabilityTableModel extends DefaultTableModel {

	private List<RemoteNode> nodes = new ArrayList<RemoteNode>();
	private Map<String,RemoteNode> nodeLookup = new HashMap<String, RemoteNode>();

	public void addNode(RemoteNode node) {
		
		if(!nodeLookup.containsKey(node.getId()))
		{
			nodes.add(node);
			nodeLookup.put(node.getId(), node);
			fireTableDataChanged();
		}
	}

	public void removeNode(RemoteNode node) {
		nodes.remove(node);
		nodeLookup.remove(node.getId());
	}

	public boolean containsNode(RemoteNode node)
	{
		return nodeLookup.containsKey(node.getId());
	}
	
	public int getRowCount() {
		
		if(nodes == null)
			return 0;
		
		return nodes.size();
	}

	public int getColumnCount() {
		
		return 6;
	}

	public boolean isCellEditable(int row, int column) {
		
		return false;
	}

	public String getColumnName(int column) {
		switch(column)
		{
			case 0: return "ID";
			case 1: return "Description";
			case 2: return "Location";
			case 3: return "Address";
			case 4: return "Type";
			case 5: return "State";
			default: return "ERROR";
		}
	}

	public Object getValueAt(int row, int column) {
		
		RemoteNode node = nodes.get(row);
		
		switch(column)
		{
			case 0: return node.getId();
			case 1: return node.getDescription();
			case 2: return node.getLocation();
			case 3: return node.getAddress();
			case 4: return node.getType();
			case 5: return node.getState();
			default: return "ERROR";
		}
	}
}
