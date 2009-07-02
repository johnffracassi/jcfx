package com.siebentag.fx.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores raw prices
 */
public class DeltaTable
{
	private Map<String,Double> values;
	
	public DeltaTable()
	{
		values = new HashMap<String,Double>();
	}

	public void addPrice(String label, double price)
	{
		values.put(label, price);
	}
	
	public int size()
	{
		return values.size();
	}
	
	public Set<String> getLabels()
	{
		return values.keySet();
	}
	
	public double getDifference(String t1, String t2)
	{
		if(values.containsKey(t1) && values.containsKey(t2))
		{
			return values.get(t2) - values.get(t1);
		}
		
		return Double.NaN;
	}
}
