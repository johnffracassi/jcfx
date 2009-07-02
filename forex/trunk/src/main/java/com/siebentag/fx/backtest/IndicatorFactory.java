package com.siebentag.fx.backtest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IndicatorFactory
{
	private static Map<String,Indicator> indicators;
	
	static
	{
		indicators = new HashMap<String, Indicator>();
	}
	
	public static Collection<Indicator> getAll()
	{
		return indicators.values();
	}
	
	public static void reset()
	{
		indicators.clear();
	}
	
	public static SimpleMovingAverage createSimpleMovingAverage(int seconds)
	{
		if(indicators.containsKey("SMA-" + seconds))
		{
			return (SimpleMovingAverage)indicators.get("SMA-" + seconds);
		}
		else
		{
			SimpleMovingAverage indicator = new SimpleMovingAverage(seconds);
			indicators.put("SMA-" + seconds, indicator);
			return indicator;
		}
	}
}
