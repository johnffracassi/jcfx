package com.jeff.fx.datasource.gain;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.util.DateUtil;


public class GAINLocator
{
	private String domain = "ratedata.gaincapital.com";
	private String pattern = "http://%s/%4d/%02d %s/%s_%s_Week%d.zip";
	
	public String generateUrl(Instrument instrument, LocalDateTime date, Period period)
	{
		int year = date.getYear();
		int month = date.getMonthOfYear();
		String cur1 = instrument.toString().substring(0, 3);
		String cur2 = instrument.toString().substring(3);
		
		String url = String.format(pattern, domain, year, month, calculateMonth(date), cur1, cur2, calculateWeek(date));
		return url;
	}
	
	private String calculateMonth(LocalDateTime date)
	{
		return DateUtil.MONTHS[roundDate(date).getMonthOfYear()-1];
	}
	
	private int calculateWeek(LocalDateTime date)
	{
		LocalDateTime rounded = roundDate(date);
		return (rounded.getDayOfMonth() - 1) / 7 + 1;
	}
	
	private LocalDateTime roundDate(LocalDateTime date)
	{
		int dayOfWeek = date.getDayOfWeek() % 7;
		
		if(dayOfWeek < 4) // round up
		{
			return date.plusDays(4 - dayOfWeek);
		}
		else if(dayOfWeek > 4)
		{
			return date.minusDays(dayOfWeek - 4);
		}
		else
		{
			return date;
		}
	}
}	
