package com.jeff.fx.util;

import java.text.SimpleDateFormat;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.Period;

public class DateUtil
{
	private static SimpleDateFormat shortDf = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat hourDf = new SimpleDateFormat("H");
	
	public static String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	
	public static String format(LocalDateTime date)
	{
		return shortDf.format(date);
	}
	
	public static String formatHour(LocalDateTime date)
	{
		return hourDf.format(date) + "h";
	}
	
	public static LocalDateTime roundDown(LocalDateTime date, Period period)
	{
		long time = date.toDateTime().toDate().getTime();
		long interval = period.getInterval();
		
		time = time - (time % interval);
		
		return new LocalDateTime(time);
	}

	public static LocalDateTime roundUp(LocalDateTime date, Period period)
	{
		long time = date.toDateTime().toDate().getTime();
		long interval = period.getInterval();
		
		time = time + (period.getInterval() - (time % interval));
		
		return new LocalDateTime(time);
	}
}
