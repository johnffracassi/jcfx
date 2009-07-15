package com.jeff.fx.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.LocalDateTime;

public class DBUtil
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private static SimpleDateFormat mysqlTime = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat mysqlDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat mysqlFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static Date toDate(java.sql.Date date, java.sql.Time time, int milliseconds) throws ParseException
	{
		 return sdf.parse(date + " " + time + "." + milliseconds);
	}

	public static Date toDate(java.sql.Date date, java.sql.Time time) throws ParseException
	{
		 return toDate(date, time, 0);
	}
	
	@SuppressWarnings("deprecation")
    public static Date toDate(java.sql.Date date) throws ParseException
	{
		 return toDate(date, new Time(0,0,0), 0);
	}
	
	
	public static LocalDateTime fromMySQL(Date date, Time time, int milliseconds)
	{
		String str = mysqlDate.format(date) + " " + mysqlTime.format(time) + "." + milliseconds;
		
		try
		{
			return mysqlFull.parse(str);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new LocalDateTime();
		}
	}

	public static LocalDateTime fromMySQL(Timestamp timestamp, int milliseconds)
	{
		String str = mysqlDate.format(timestamp) + " " + mysqlTime.format(timestamp) + "." + milliseconds;
		
		try
		{
			return mysqlFull.parse(str);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new LocalDateTime(0, 0, 0, 0, 0, 0);
		}
	}
	
	
	public static String formatMySQLTime(LocalDateTime date)
	{
		return mysqlTime.format(date);
	}
	
	public static String formatMySQLDate(LocalDateTime date)
	{
		return mysqlDate.format(date);
	}	
}
