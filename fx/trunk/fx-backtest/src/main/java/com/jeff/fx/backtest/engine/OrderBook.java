package com.jeff.fx.backtest.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBook {
	
	private Map<Integer,BTOrder> lookup;
	private List<BTOrder> openOrders;
	private List<BTOrder> closedOrders;
	
	private int nextId = 1;
	
	public OrderBook() {
		openOrders = new ArrayList<BTOrder>();
		closedOrders = new ArrayList<BTOrder>();
		lookup = new HashMap<Integer, BTOrder>();
	}
	
	public List<BTOrder> getOpenOrders() {
		return openOrders;
	}
	
	public BTOrder find(int id) {
		return lookup.get(id);
	}
	
	public int post(BTOrder order) {
		order.setId(nextId++);
		openOrders.add(order);
		lookup.put(order.getId(), order);
		return order.getId();
	}
	
	public void close(BTOrder order) {
		openOrders.remove(order);
		closedOrders.add(order);
	}
	
	public boolean hasOpenOrders() {
		return openOrders.size() > 0;
	}
	
	public double balance() {
		
		double balance = 0.0;
		
		for(BTOrder order : closedOrders) {
			balance += order.getProfit();
		}
		
		return balance;
	}
}
