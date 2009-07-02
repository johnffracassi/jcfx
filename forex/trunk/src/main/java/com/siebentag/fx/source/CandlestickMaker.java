package com.siebentag.fx.source;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.collator.TickCollection;
import com.siebentag.fx.loader.CandleDataDAO;
import com.siebentag.fx.loader.TickDataDAO;

@Component
public class CandlestickMaker
{
	@Autowired
	CandleDataDAO candleDao;
	
	@Autowired
	TickDataDAO tickDao;
	
	public static void main(String[] args)
	{
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    CandlestickMaker app = (CandlestickMaker)ctx.getBean("candlestickMaker");
	    app.run();
	}
	
	public void run()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2009, Calendar.APRIL, 10, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Date endDate = cal.getTime();
		
		for(Instrument instrument : Instrument.values())
		{
			cal.set(2008, Calendar.AUGUST, 1, 0, 0, 0);
			
			while(cal.getTime().before(endDate))
			{
				try
				{
					System.out.println("Loading ticks from database (" + cal.getTime() + " / " + instrument + ")");
					List<TickDataPoint> ticks = tickDao.find(FXDataSource.GAIN, instrument, cal.getTime(), 86400);
					build(ticks, cal.getTime());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				cal.add(Calendar.DATE, 1);
			}
		}
	}
	
	/**
	 * @param ticks a list of the days ticks
	 */
	public void build(List<TickDataPoint> ticks, Date startDate)
	{
		List<CandleStickDataPoint> candles = new LinkedList<CandleStickDataPoint>();
		
		candles.addAll(build(startDate, ticks, 1 * 60));
		candles.addAll(build(startDate, ticks, 5 * 60));
		candles.addAll(build(startDate, ticks, 15 * 60));
		candles.addAll(build(startDate, ticks, 30 * 60));
		candles.addAll(build(startDate, ticks, 1 * 60 * 60));
		candles.addAll(build(startDate, ticks, 2 * 60 * 60));
		candles.addAll(build(startDate, ticks, 3 * 60 * 60));
		candles.addAll(build(startDate, ticks, 4 * 60 * 60));
		candles.addAll(build(startDate, ticks, 6 * 60 * 60));
		candles.addAll(build(startDate, ticks, 12 * 60 * 60));
		candles.addAll(build(startDate, ticks, 24 * 60 * 60));
		
		candleDao.load(candles);
	}
	
	/**
	 * 
	 * @param ticks
	 * @param minutes
	 * @return
	 */
	private List<CandleStickDataPoint> build(Date startDate, List<TickDataPoint> ticks, int seconds)
	{
//		System.out.println("  Building candles for " + seconds/60 + "m");
		
		// return empty candle list if nothing passed in
		if(ticks.size() == 0)
		{
			return Collections.<CandleStickDataPoint>emptyList();
		}
		
		// setup some constants
		String instrument = ticks.get(0).getInstrument();
		FXDataSource dataSource = ticks.get(0).getDataSource();
		
		// return list
		List<CandleStickDataPoint> candles = new LinkedList<CandleStickDataPoint>();

		// find out the start date/time
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		
		TickCollection tickCollection = new TickCollection(startDate); 

		Date candleStart = cal.getTime();
		cal.add(Calendar.SECOND, seconds);
		Date candleEnd = cal.getTime();
		
		for(TickDataPoint tick : ticks)
		{
			// if we aren't still in the same candle as before
			if(!tick.getDate().before(candleEnd))
			{
				if(tickCollection.size() > 0)
				{
					// initialise the first candle
					candles.add(createCandle(tickCollection, dataSource, instrument, candleStart, seconds));
					tickCollection.reset();
				}
				
				// advance the calendar, to point to the end of the candle
				candleStart = cal.getTime();
				cal.add(Calendar.SECOND, seconds);
				candleEnd = cal.getTime();
			}
			
			tickCollection.add(tick);
		}

		if(tickCollection.size() > 0)
		{
			candles.add(createCandle(tickCollection, dataSource, instrument, candleStart, seconds));
		}
		
		return candles;
	}
	
	private CandleStickDataPoint createCandle(TickCollection tickCollection, FXDataSource dataSource, String instrument, Date startDate, int seconds)
	{
		CandleStickDataPoint candle = tickCollection.toCandleStick(seconds);
		candle.setDataSource(dataSource);
		candle.setInstrument(instrument);
		candle.setDate(startDate);
		candle.setPeriod((seconds / 60) + "m");		
		return candle;
	}
}
