package com.jeff.fx.datasource.converter;

import java.util.ArrayList;
import java.util.Collections;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Period;

public class CandleCollection extends ArrayList<CandleDataPoint>
{
	public CandleDataPoint toCandle(Period period)
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
			CandleDataPoint candle = new CandleDataPoint(get(0));
			candle.setPeriod(period);
			
			for(int i=1; i<size(); i++)
			{
				candle.merge(get(i));
			}
			
//			if(candle.getDate().getMinuteOfHour() == 1) 
//			{
//				candle.getDate().setMinutes(0);
//			}
			
			return candle;
		}
	}
}