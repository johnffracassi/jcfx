package com.siebentag.fx.collator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.loader.CandleDataDAO;
import com.siebentag.fx.loader.TickDataDAO;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class CandlestickBuilder
{
	@Autowired
	private TickDataDAO dao;
	
	@Autowired
	private CandleDataDAO candleDao;
	
	public static void main(String[] args)
	{
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    CandlestickBuilder builder = (CandlestickBuilder)ctx.getBean("candlestickBuilder");
		builder.run();
	}
	
	public void run()
	{
		for(Instrument instrument : new Instrument[] { Instrument.NZDUSD, Instrument.USDCAD, Instrument.USDCHF, Instrument.USDJPY })
		{
			Calendar cal = Calendar.getInstance();
			cal.set(2008, Calendar.JANUARY, 1, 0, 0, 0);
			Date endTime = cal.getTime();
			
			cal.set(2009, Calendar.MAY, 1, 16, 0, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			while(cal.getTime().after(endTime))
			{
				try
				{
					List<TickDataPoint> ticks = TickDataDAO.find(FXDataSource.GAIN, instrument, cal.getTime(), 3600);
					
					System.out.println(instrument + " - " + cal.getTime() + " (ticks=" + ticks.size() + ")");
					
					if(ticks.size() > 0)
					{
						for(Integer period : new int[] { 60, 300, 600, 900, 1800, 3600 })
						{
							List<CandleStickDataPoint> candles = TickCollator.process(ticks, 3600, period);
							CandleDataDAO.load(candles);
						}
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				cal.add(Calendar.HOUR, -1);
			}
		}
	}
}
