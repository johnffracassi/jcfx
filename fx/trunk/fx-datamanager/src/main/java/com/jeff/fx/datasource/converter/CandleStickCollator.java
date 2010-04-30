package com.jeff.fx.datasource.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalTime;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Period;

public class CandleStickCollator
{
	public static List<CandleDataPoint> collate(List<CandleDataPoint> candles, Period sourcePeriod, Period targetPeriod)
	{
		// map candles to candle collections
		Map<String,CandleCollection> candleMap = new HashMap<String, CandleCollection>();
		for(CandleDataPoint candle : candles)
		{
			String hash = getHashCode(candle, targetPeriod);
			
			CandleCollection bucket = candleMap.get(hash);
			
			if(bucket == null)
			{
				bucket = new CandleCollection();
				candleMap.put(hash, bucket);
			}
			
			bucket.add(candle);
		}
		
		// build new candle list
		List<CandleDataPoint> newCandles = new ArrayList<CandleDataPoint>();
		for(CandleCollection candleCollection : candleMap.values())
		{
			newCandles.add(candleCollection.toCandle(targetPeriod));
		}
		
		return newCandles;
	}
	
	private static final String getHashCode(CandleDataPoint candle, Period targetPeriod)
	{
		LocalTime time = candle.getDate().toLocalTime();
		int minuteOfDay = time.getHourOfDay() * 60 + time.getMinuteOfHour();
		int periodOfDay = minuteOfDay / (int)(targetPeriod.getInterval() / 60000);
		
		return candle.getInstrument() + "-" + periodOfDay;
	}
}
