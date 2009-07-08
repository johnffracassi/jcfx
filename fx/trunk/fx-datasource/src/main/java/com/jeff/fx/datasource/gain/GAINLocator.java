package com.jeff.fx.datasource.gain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.util.DateUtil;


public class GAINLocator
{
	private String domain = "ratedata.gaincapital.com";
	
	public List<String> generateUrls(Instrument instrument, LocalDateTime date, Period period)
	{
		String pattern = "http://%s/%4d/%2d %s/%s_%s_Week%d.zip";
		
		List<String> urls = new ArrayList<String>(5);
		for(int i=1; i<=5; i++)
		{
			urls.add(String.format(pattern, domain, date.getYear(), date.getMonthOfYear(), DateUtil.MONTHS[date.getMonthOfYear()-1], instrument.toString().substring(0, 3), instrument.toString().substring(3), i));
		}
		
		return urls;
	}
}	
