package com.siebentag.fx.collator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.TickDataPoint;

public class TickCollator
{
	/**
	 * Pass in one hours worth of ticks. 
	 */
	public static List<CandleStickDataPoint> process(List<TickDataPoint> ticksList, int candleSizeSeconds)
	{
		return process(ticksList, 3600, candleSizeSeconds);
	}
	
	public static List<CandleStickDataPoint> process(List<TickDataPoint> ticksList, int tickTimeSpan, int candleSizeSeconds)
	{
		// time of the first tick
		long openingTime = ticksList.get(0).getDate().getTime();
		
		// open time of the first candle
		long firstCandleTime = openingTime - (openingTime % (tickTimeSpan * 1000));
		
		// opening time of the last candle
		long lastCandleTime = firstCandleTime + (tickTimeSpan * 1000);

		// number of candles
		int windows = (int)((lastCandleTime - firstCandleTime) / (candleSizeSeconds * 1000));
		
		// create tickCollections
		TickCollection[] periods = new TickCollection[windows];
		for(int i=0; i<periods.length; i++)
		{
			periods[i] = new TickCollection(new Date(firstCandleTime + (i*1000*candleSizeSeconds)));
		}
		
		// collate tick data
		for(TickDataPoint tick : ticksList)
		{
			long millis = tick.getDate().getTime() - firstCandleTime;
			int index = getIntervalIndex(millis, candleSizeSeconds);
			
			if(index < periods.length)
			{
				periods[index].add(tick);
			}
			else
			{
				System.out.println("invalid index " + index + " for time " + tick.getDate());
			}
		}
		
		// convert collections to candlesticks
		List<CandleStickDataPoint> candles = new ArrayList<CandleStickDataPoint>(windows);
		for(int i=0; i<periods.length; i++)
		{
			CandleStickDataPoint candle = periods[i].toCandleStick(candleSizeSeconds);
			
			// if there are no ticks in the candle, then approximate the values from previous candle
			if(candle.getTickCount() == 0)
			{
				if(candles.size() > 0)
				{
					CandleStickDataPoint lastCandle = candles.get(candles.size() - 1);
					candle.setApproximatedValues(lastCandle);
				}
			}
			
			candles.add(candle);
		}
		
		return candles;
	}
	
	private static int getIntervalIndex(long millis, int interval)
	{
		return (int)((millis / 1000) / interval);
	}
}

