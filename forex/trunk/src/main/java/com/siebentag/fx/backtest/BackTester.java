package com.siebentag.fx.backtest;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Component;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.loader.TickDataDAO;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class BackTester 
{
	private FXDataSource dataSource;
	private Date startDate;
	private Date endDate;
	private Instrument instrument;
	
	private List<Strategy> strategies;
	
	private static final int LOAD_INCREMENT_UNIT = Calendar.SECOND;
	private static final int LOAD_INCREMENT_AMOUNT = 86400;
	
	public BackTester()
    {
    }

	public void init()
	{
		strategies = new LinkedList<Strategy>();
		
		strategies.addAll(new TimeBasedStrategyFactory().buildStrategies(instrument));
		
//		int[] sma1 = { 180 };
//		int[] sma2 = { 300 };
//		int[] sma3 = { 900 };
//		double[] stops = { 25 };
//		double[] diffs = { 1, 3, 5, 7, 9 };

//		int[] sma1 = { 60, 180, 300, 600, 900 };
//		int[] sma2 = { 300, 600, 900, 1800, 2700, 3600 };
//		int[] sma3 = { 900, 1800, 3600, 5400, 7200 };
//		double[] stops = { 25, 50, 75, 100 };
//		double[] diffs = { 1, 5, 10, 25, 50, 100 };
//		
//		for(Integer s1 : sma1)
//		{
//			for(Integer s2 : sma2)
//			{
//				for(Integer s3 : sma3)
//				{
//					if(s1 < s2 && s2 < s3)
//					{
//						for(Double diff : diffs)
//						{
//							for(Double sl : stops)
//							{
//								SimpleScalperStrategy scalper = new SimpleScalperStrategy(new BalanceSheet());
//								scalper.setInstrument(Instrument.GBPUSD);
//								scalper.setLotSize(0.5);
//								scalper.setSMASizes(s1, s2, s3);
//								scalper.setDifference(diff);
//								scalper.setStopLoss(sl);
//								scalper.registerIndicators();
//								strategies.add(scalper);
//							}
//						}
//					}
//				}
//			}
//		}
	}
	
	public void run()
	{
		// set the calendar to startDate
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		
		// set from/to dates to startDate, startDate + interval
		cal.setTime(startDate);
		Date from = cal.getTime();
		cal.add(LOAD_INCREMENT_UNIT, LOAD_INCREMENT_AMOUNT);

		long time = System.nanoTime();
		
		System.out.printf("Running %1d strategies, from %s to %s%n", strategies.size(), from, endDate);
		
		// keep going until we reach the end date
		while(from.before(endDate))
		{
			long time2 = System.nanoTime();
			
			System.out.println("==| " + from);
			
			// ticks for all instruments
			List<TickDataPoint> ticks = new Vector<TickDataPoint>(1000, 1000);
			
			// load each tick, instrument by instrument
//			for(Instrument instrument : instruments)
//			{
				ticks.addAll(loadTicks(dataSource, instrument, from, LOAD_INCREMENT_AMOUNT));
//			}
			
			// sort all ticks by time (disregarding instrument)
			Collections.sort(ticks);
			
			// pre-calculate indicators
			Collection<Indicator> indicators = IndicatorFactory.getAll();
			IndicatorValueCache.reset();
			System.out.printf("  pre-calculating indicators (%1d)%n", indicators.size());
			for(Indicator indicator : indicators)
			{
				for(TickDataPoint tick : ticks)
				{
					indicator.tick(tick);
				}
			}
			
			// alert strategies 
			System.out.printf("  running strategies (count=%1d,ticks=%1d)%n", strategies.size(), ticks.size());
			if(ticks.size() > 0)
			{
				StrategyExecutor executor = new MultiThreadedStrategyExecutor(strategies, ticks);
				executor.run();
			}

			// increment to/from by one hour each
			from = cal.getTime();
			cal.add(LOAD_INCREMENT_UNIT, LOAD_INCREMENT_AMOUNT);
			
			double totalTime = (System.nanoTime() - time2)/1000000000.0;
			System.out.printf("  %.2fs (%.1fps)\n", totalTime, strategies.size() / totalTime);
		} 
		
		
		File summaryFile = new File("/mnt/sata1500b/dev/forex/reports/", strategies.get(0).getSummaryFile());
		File reportsDir = summaryFile.getParentFile();
		try
		{
			StrategySummaryReport.saveReport(strategies, summaryFile);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		for(Strategy strategy : strategies)
		{
			try
			{
				OrderBookReport.saveReport(strategy.getBalanceSheet(), new File(reportsDir, strategy.getName() + ".html"));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

		System.out.printf("Completed in %.1fs\n", (System.nanoTime() - time) / 1000000000.0);
	}
	
	/**
	 * Load ticks from the database
	 * 
	 * @param dataSource
	 * @param instrument
	 * @param from
	 * @param to
	 * @return
	 */
	private List<TickDataPoint> loadTicks(FXDataSource dataSource, Instrument instrument, Date from, int interval) 
	{
		List<TickDataPoint> ticks = new ArrayList<TickDataPoint>(); 

		try
		{
			ticks = TickDataDAO.find(dataSource, instrument, from, interval);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return ticks;
	}

	public Date getStartDate()
    {
    	return startDate;
    }

	public void setStartDate(Date startDate)
    {
    	this.startDate = startDate;
    }

	public List<Strategy> getStrategies()
    {
    	return strategies;
    }

	public void setStrategies(List<Strategy> strategies)
    {
    	this.strategies = strategies;
    }

	public FXDataSource getDataSource()
    {
    	return dataSource;
    }

	public void setDataSource(FXDataSource dataSource)
    {
    	this.dataSource = dataSource;
    }

	public Instrument getInstrument()
    {
    	return instrument;
    }

	public void setInstrument(Instrument instruments)
    {
    	this.instrument = instruments;
    }

	public Date getEndDate() 
	{
		return endDate;
	}

	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}

}
