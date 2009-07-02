package com.siebentag.fx.source.gain;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.source.GenericDownloader;

public class GainDownloader extends GenericDownloader<CandleStickDataPoint>
{
	String[] months = {
		"January", "February", "March", "April", "May", "June", 
		"July", "August", "September", "October", "November", "December"			
	};
	
	public GainDownloader()
	{
	}
	
	public static void main(String[] args)
    {
	    GainDownloader dd = new GainDownloader();
		dd.run();
    }

	public void run()
	{
		List<String> urls = generateUrls();
		for(String url : urls)
		{
			System.out.println(url);
		}
	}
	
	public List<String> generateUrls()
	{
		List<String> urls = new ArrayList<String>();
		
		try
		{
			String url = "wget http://ratedata.gaincapital.com/{0,number,0000}/{1,number,00}%20{2}/{3}_Week{4}.zip -O {0,number,0000}-{1,number,00}-{3}-Week{4}.zip";
			
			for(int year = 2009; year <= 2009; year++)
			{
				for(int month = 3; month < 4; month ++)
				{
					for(int week = 3; week <= 5; week ++)
					{
						for(GainInstruments ins : GainInstruments.values())
						{
							urls.add(MessageFormat.format(url, year, month+1, months[month], ins, week));
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return urls;
	}

	@Override
    public CandleStickDataPoint parse(String line) throws ParseException
    {
	    return null;
    }
}

