package com.siebentag.fx.backtest;

import java.util.Date;
import java.util.HashMap;

public class IndicatorValueCache
{
	private static HashMap<String,HashMap<Date,Double>> cache = new HashMap<String,HashMap<Date,Double>>();
	
	public static void reset()
	{
		cache.clear();
	}
	
	public static double getValue(String indicator, Date date)
	{
		return cache.get(indicator).get(date);
	}
	
	public static void setValue(String indicator, Date date, double value)
	{
		HashMap<Date,Double> indicatorCache = cache.get(indicator);
		
		if(indicatorCache == null)
		{
			indicatorCache = new HashMap<Date, Double>();
			cache.put(indicator, indicatorCache);
		}

		indicatorCache.put(date, value);
	}
}
