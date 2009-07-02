package com.siebentag.fx.backtest;

import java.util.List;
import java.util.Vector;

public class BalanceSheet
{
	private double openingBalance;
	private double cachedBalance;
	private List<Order> orders;
	
	public BalanceSheet()
	{
		orders = new Vector<Order>();
		openingBalance = 10000.0;
	}
	
	public String toString()
	{
		return calculateBalance() + "\t" + calculateMaximumDrawdown() + "\t" + orderCount();
	}
	
	public void postOrder(Order order)
	{
		cachedBalance = Double.NaN;
		orders.add(order);
	}
	
	public void closeOrder(Order order)
	{
		cachedBalance = Double.NaN;
	}
	
	public int wins()
	{
		int count = 0;
		
		for(Order order : orders)
		{
			if(order.getProfitLoss() > 0.0)
			{
				count ++;
			}
		}
		
		return count;
	}
	
	public int losses()
	{
		int count = 0;
		
		for(Order order : orders)
		{
			if(order.getProfitLoss() < 0.0)
			{
				count ++;
			}
		}
		
		return count;
	}
	
	public List<Order> getOpenOrders()
	{
		List<Order> openOrders = new Vector<Order>();
		
		for(Order order : orders)
		{
			if(order.isOpen())
			{
				openOrders.add(order);
			}
		}
		
		return openOrders;
	}
	
	public List<Order> getClosedOrders()
	{
		List<Order> openOrders = new Vector<Order>();
		
		for(Order order : orders)
		{
			if(order.isClosed())
			{
				openOrders.add(order);
			}
		}
		
		return openOrders;
	}

	public List<Order> getOrders()
	{
		return orders;
	}
	
	public int[] getCloseTypeCount()
	{
		int[] types = new int[4];
		
		for(Order order : orders)
		{
			switch(order.getCloseType())
			{
				case Close: types[0]++; break;
				case Limit: types[1]++; break;
				case StopLoss: types[2]++; break;
				case Open: types[3]++; break;
			}
		}
		
		return types;
	}
	
	public double getBalance()
	{
		if(Double.isNaN(cachedBalance))
		{
			return calculateBalance();
		}
		else
		{
			return cachedBalance;
		}
	}
	
	public double calculateBalance()
	{
		cachedBalance = openingBalance + calculateProfitForAllOrders();
		return cachedBalance;
	}
	
	public int orderCount()
	{
		return orders.size();
	}
	
	public double calculateMaximumDrawdown()
	{
		double bal = openingBalance;
		double low = bal;
		double high = bal;
		double maxDD = 0.0;
		
		for(Order order : orders)
		{
			bal += order.getProfitLoss();
			
			if(bal > high)
			{
				high = bal;
				low = bal;
			}
			else if(bal < low)
			{
				low = bal;
			}
			
			double dd = high - low;
			
			if(dd > maxDD)
			{
				maxDD = dd;
			}
		}
		
		return maxDD;
	}
	
	public double calculateProfitForAllOrders()
	{
		double value = 0.0;
		
		for(Order order : orders)
		{
			value += order.getProfitLoss();
		}
		
		return value;
	}
	
//	public double calculateProfitForOpenOrders()
//	{
//		double value = 0.0;
//		
//		for(Order order : getOpenOrders())
//		{
//			value += order.getValue();
//		}
//		
//		return value;
//	}
//	
//	public double calculateProfitForClosedOrders()
//	{
//		double value = 0.0;
//		
//		for(Order order : getClosedOrders())
//		{
//			value += order.getValue();
//		}
//		
//		return value;
//	}
}
