package com.siebentag.fx.backtest;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import com.siebentag.fx.TickDataPoint;

/**
 * Maintain a simple moving average.
 * 
 * @author jeff
 */
public class SimpleMovingAverage implements Indicator
{
	private Queue<TickDataPoint> ticks;
	private int seconds;

	public SimpleMovingAverage(int seconds)
	{
		this.seconds = seconds;
		ticks = new LinkedList<TickDataPoint>();
	}
	
	public double getValue(Date date)
	{
		return IndicatorValueCache.getValue(getName(), date);
	}
	
	public double calculateValue()
	{
		double sum = 0.0;
		
		for(TickDataPoint tick : ticks)
		{
			sum += tick.getAveragePrice();
		}
		
		return sum / ticks.size();
	}

	public void tick(TickDataPoint tick)
	{
		// add the newest tick
		ticks.add(tick);
		
		// remove old ticks from the front of the queue
		if(ticks.size() > 0)
		{
			while(ticks.peek().getDate().getTime() < tick.getDate().getTime() - seconds*1000)
			{
				ticks.poll();
			}
		}
		
		IndicatorValueCache.setValue(getName(), tick.getDate(), calculateValue());
	}
	
	public String getName()
	{
		return "SMA(" + (seconds/60) + ")";
	}
	
	public String toString()
	{
		return getName();
	}
	
	public int hashCode()
	{
		return toString().hashCode();
	}
	
	public boolean equals(Object obj)
	{
		return toString().equals(obj.toString());
	}
}

