package com.siebentag.fx.backtest;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;


public class BackTestRunner
{
	public static void main(String[] args)
	{
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    BackTester backTester = (BackTester)ctx.getBean("backTester");
	    
	    Calendar cal = Calendar.getInstance();
	    cal.set(2009, Calendar.FEBRUARY, 1, 0, 0, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    Date start = cal.getTime();
	    
	    cal.set(2009, Calendar.MAY, 1, 0, 0, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    Date end = cal.getTime();

	    for(Instrument instrument : new Instrument[] { Instrument.EURGBP, Instrument.AUDUSD, Instrument.EURAUD, Instrument.GBPUSD, Instrument.GBPCHF, Instrument.EURUSD, Instrument.EURCHF, Instrument.USDCHF, Instrument.USDCAD })
	    {
		    backTester.setDataSource(FXDataSource.GAIN);
		    backTester.setStartDate(start);
		    backTester.setEndDate(end);
		    backTester.setInstrument(instrument);
		    backTester.init();
		    backTester.run();
	    }
	}
}
