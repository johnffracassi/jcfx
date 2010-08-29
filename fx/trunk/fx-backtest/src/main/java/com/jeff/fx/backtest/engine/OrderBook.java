package com.jeff.fx.backtest.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBook {
	
	private Map<Integer,BTOrder> lookup;
	private List<BTOrder> openOrders = new ArrayList<BTOrder>();
	private List<BTOrder> closedOrders = new ArrayList<BTOrder>();
	
	private int nextId = 1;
	private int openOrderCount = 0;
	private int closedOrderCount = 0;
	
	public OrderBook() {
		lookup = new HashMap<Integer, BTOrder>();
	}
	
	public List<BTOrder> getOpenOrders() {
		return openOrders;
	}
	
	public List<BTOrder> getClosedOrders() {
		return closedOrders;
	}
	
	public BTOrder find(int id) {
		return lookup.get(id);
	}
	
	public int post(BTOrder order) {
		order.setId(nextId++);
		openOrders.add(order);
		lookup.put(order.getId(), order);
		updateCounts();
		return order.getId();
	}
	
	public void close(BTOrder order) {
		openOrders.remove(order);
		closedOrders.add(order);
		updateCounts();
	}
	
	private void updateCounts() {
		openOrderCount = openOrders.size();
		closedOrderCount = closedOrders.size();
	}
	
	public boolean hasOpenOrders() {
		return openOrderCount > 0;
	}
	
	public double balance() {
		
		double balance = 0.0;
		
		for(BTOrder order : closedOrders) {
			balance += order.getProfit();
		}
		
		return balance;
	}

	public int getOpenOrderCount() {
		return openOrderCount;
	}

	public int getClosedOrderCount() {
		return closedOrderCount;
	}
}
