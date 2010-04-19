package com.jeff.fx.datasource.gain;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.Locator;
import com.jeff.fx.util.DateUtil;

@Component
public class GAINLocator implements Locator
{
	private String domain = "ratedata.gaincapital.com";
	private String pattern = "http://%s/%4d/%02d %s/%s_%s_Week%d.zip";
	
	public String generateUrl(Instrument instrument, LocalDate date, Period period)
	{
		int year = date.getYear();
		int month = calculateMonth(date);
		String cur1 = instrument.toString().substring(0, 3);
		String cur2 = instrument.toString().substring(3);
		
		String url = String.format(pattern, domain, year, month+1, DateUtil.MONTHS[month], cur1, cur2, calculateWeek(date));
		return url;
	}
	
	private int calculateMonth(LocalDate date) {
		return roundDate(date).getMonthOfYear() - 1;
	}
	
	private int calculateWeek(LocalDate date)
	{
		LocalDate rounded = roundDate(date);
		return (rounded.getDayOfMonth() - 1) / 7 + 1;
	}
	
	private LocalDate roundDate(LocalDate date)
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
