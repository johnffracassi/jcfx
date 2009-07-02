package com.siebentag.fx.source.dukascopy;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.source.GenericDownloader;

public class DukascopyScripter extends GenericDownloader<CandleStickDataPoint>
{
	public DukascopyScripter()
	{
	}
	
	public static void main(String[] args)
    {
	    DukascopyScripter dd = new DukascopyScripter();
		dd.run();
    }

	public void run()
	{
		List<String> urls = generateCommands();
		for(String url : urls)
		{
			System.out.println("wget " + url);
		}
	}
	
	public static String generateUrl(String instrument, Date date, int hr)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return generateUrl(instrument, year, month, day, hr);
	}
	
	public static String generateUrl(String instrument, int year, int month, int day, int hr)
	{
		String cmd = "http://www.dukascopy.com/datafeed/{0}/{1,number,0000}/{2,number,00}/{3,number,00}/{4,number,00}h_ticks.bin";
		return MessageFormat.format(cmd, instrument, year, month, day, hr, month+1);
	}
	
	public static String generateOutputFile(String instrument, Date date, int hr)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return generateOutputFile(instrument, year, month, day, hr);
	}
	
	public static String generateOutputFile(String instrument, int year, int month, int day, int hr)
	{
		String filename = "{0}-{1,number,0000}{5,number,00}{3,number,00}-{4,number,00}h_ticks.zip";
		return MessageFormat.format(filename, instrument, year, month, day, hr, month+1);
	}
	
	public static List<String> generateCommands()
	{
		List<String> commands = new ArrayList<String>();

		try
		{
			for(int year = 2009; year <= 2009; year++)
			{
				for(int month = 2; month <= 2; month ++)
				{
					for(int day = 1; day <= 31; day++)
					{
						for(int hr = 0; hr <= 23; hr ++)
						{
							for(String instrument : new String[] { "AUDJPY", "AUDUSD", "EURCHF", "EURGBP", "EURUSD", "GBPCHF", "GBPJPY", "GBPUSD", "USDCHF", "USDJPY"})
							{
								commands.add("wget " + generateUrl(instrument, year, month, day, hr) + " -O " + generateOutputFile(instrument, year, month, day, hr));
							}
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return commands;
	}

	@Override
    public CandleStickDataPoint parse(String line) throws ParseException
    {
	    return null;
    }
}

