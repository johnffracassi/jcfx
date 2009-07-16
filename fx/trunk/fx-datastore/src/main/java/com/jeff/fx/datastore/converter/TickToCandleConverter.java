package com.jeff.fx.datastore.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.util.DateUtil;

public class TickToCandleConverter 
{
	public List<CandleDataPoint> convert(List<TickDataPoint> ticks, Period period)
	{
		if(ticks == null || period == null || ticks.size() == 0)
		{
			return Collections.<CandleDataPoint>emptyList();
		}
		
		// order the list by date
		Collections.sort(ticks);
		
		// find the first and last tick dates
		LocalDateTime startDateTime = DateUtil.roundDown(ticks.get(0).getDate(), period);
		LocalDateTime endDateTime = DateUtil.roundUp(ticks.get(ticks.size()-1).getDate(), period);
		
		// work out how many periods we need
		int seconds = Seconds.secondsBetween(startDateTime, endDateTime).getSeconds();
		int count = seconds / (int)(period.getInterval() / 1000);
		
		// create and initialise the initial candles
		TickCollection[] candles = new TickCollection[count];
		LocalDateTime currentTime = new LocalDateTime(startDateTime);
		for(int i=0; i<candles.length; i++)
		{
			candles[i] = new TickCollection(currentTime);			
			currentTime = currentTime.plusMillis((int)period.getInterval());
		}
		
		// file each tick into its collection
		for(TickDataPoint tick : ticks)
		{
			int idx = calculateIndex(startDateTime, endDateTime, period);
			candles[idx].add(tick);
		}
		
		// convert each tickCollection to a candle
		List<CandleDataPoint> candleList = new ArrayList<CandleDataPoint>(count);
		for(int i=0; i<count; i++)
		{
			candleList.set(i, candles[i].toCandle(period));
		}
		
		// fix any candles without ticks in them
		for(int i=1; i<count; i++)
		{
			if(candleList.get(i).getTickCount() == 0)
			{
				candleList.get(i).setApproximatedValues(candleList.get(i-1), true);
			}
		}
		
		// fix first candle if it doesn't contain ticks
		if(candleList.get(0).getTickCount() == 0)
		{
			// find first non-zero candle
			int idx = 1;
			for(; idx < candleList.size(); idx++) 
			{
				if(candleList.get(idx).getTickCount() > 0)
				{
					break;
				}
			}
			
			// now copy back to the first element
			for(; idx > 0; idx--)
			{
				candleList.get(idx).setApproximatedValues(candleList.get(idx+1), false);
			}
		}
		
		return candleList;
	}
	
	private int calculateIndex(LocalDateTime startTime, LocalDateTime actualTime, Period period)
	{
		int secondsDiff = Seconds.secondsBetween(startTime, actualTime).getSeconds();
		return (int)(secondsDiff / (period.getInterval() / 1000));
	}
}
