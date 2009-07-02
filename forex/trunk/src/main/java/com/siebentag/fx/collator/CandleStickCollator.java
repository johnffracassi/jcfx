package com.siebentag.fx.collator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalTime;

import com.siebentag.fx.CandleStickDataPoint;

public class CandleStickCollator
{
	public static List<CandleStickDataPoint> collate(List<CandleStickDataPoint> candles, int sourcePeriod, int targetPeriod)
	{
		// map candles to candle collections
		Map<String,CandleCollection> candleMap = new HashMap<String, CandleCollection>();
		for(CandleStickDataPoint candle : candles)
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
		List<CandleStickDataPoint> newCandles = new ArrayList<CandleStickDataPoint>();
		for(CandleCollection candleCollection : candleMap.values())
		{
			newCandles.add(candleCollection.toCandle(targetPeriod));
		}
		
		return newCandles;
	}
	
	private static String getHashCode(CandleStickDataPoint candle, int targetPeriod)
	{
		LocalTime time = candle.getLocalDateTime().toLocalTime();
		int minuteOfDay = time.getHourOfDay() * 60 + time.getMinuteOfHour();
		int periodOfDay = minuteOfDay / (targetPeriod / 60);
		
		return candle.getInstrument() + "-" + periodOfDay;
	}
}

class CandleCollection extends ArrayList<CandleStickDataPoint>
{
	public CandleStickDataPoint toCandle(int period)
	{
		if(size() == 0)
		{
			return null;
		}
		else if(size() == 1)
		{
			return get(0);
		}
		else
		{
			Collections.sort(this);
			CandleStickDataPoint candle = new CandleStickDataPoint(get(0));
			candle.setPeriod(String.valueOf(period));
			
			for(int i=1; i<size(); i++)
			{
				candle.merge(get(i));
			}
			
			if(candle.getDate().getMinutes() == 1) candle.getDate().setMinutes(0);
			
			return candle;
		}
	}
}