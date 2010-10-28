package com.jeff.fx.backtest.indicator;

import org.joda.time.LocalDate;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.indicator.indicator.AbstractIndicator;
import com.jeff.fx.indicator.indicator.CommodityChannelIndex;

public class MovingAverageBenchmark 
{
	public static void main(String[] args)
    {
	    int periods = 14;
	    int weeks = 500;
	    int repetitions = 10;
	    
		CandleCollection cc = createCandles(weeks, 0.00001f);
	    AbstractIndicator indicator = new CommodityChannelIndex(periods);
        
	    long startTime = System.nanoTime();
	    for(int i=0; i<repetitions; i++)
	    {
    		indicator.calculate(cc);
    		indicator.invalidate();
	    }
		long time = System.nanoTime() - startTime;

		System.out.printf("%s over %d weeks took %.4fs", indicator.getDisplayName(), weeks, time / 1000000000.0 / repetitions); 
	}

	public static CandleCollection createCandles(int weeks, float increment)
	{
	    CandleCollection cc = new CandleCollection();
	    
	    LocalDate startDate = new LocalDate(1990, 1, 1);
	    
		for(int w=0; w<weeks; w++)
		{
		    LocalDate date = startDate.plusWeeks(w);
    	    CandleWeek cw = new CandleWeek(date, FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin);
    		
    		for(int i=0; i<cw.getCandleCount(); i++)
    		{
    			CandleDataPoint candle = cw.getCandle(i);
    			candle.setPrice(0.9000 + (increment * i));
    			cw.setCandle(candle);
    		}
            cc.putCandleWeek(cw);
		}
		
		return cc;
	}
}
