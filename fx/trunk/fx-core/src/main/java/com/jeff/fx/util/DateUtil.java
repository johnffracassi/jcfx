package com.jeff.fx.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
	private static SimpleDateFormat shortDf = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat hourDf = new SimpleDateFormat("H");
	private static SimpleDateFormat mysqlTime = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat mysqlDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat mysqlFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	
	public static String format(Date date)
	{
		return shortDf.format(date);
	}
	
	public static String formatHour(Date date)
	{
		return hourDf.format(date) + "h";
	}
	
	public static String formatMySQLTime(Date date)
	{
		return mysqlTime.format(date);
	}
	
	public static String formatMySQLDate(Date date)
	{
		return mysqlDate.format(date);
	}
	
	public static Date fromMySQL(java.sql.Date date, java.sql.Time time, int milliseconds)
	{
		String str = mysqlDate.format(date) + " " + mysqlTime.format(time) + "." + milliseconds;
		
		try
		{
			return mysqlFull.parse(str);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new Date();
		}
	}

	public static Date fromMySQL(Timestamp timestamp, int milliseconds)
	{
		String str = mysqlDate.format(timestamp) + " " + mysqlTime.format(timestamp) + "." + milliseconds;
		
		try
		{
			return mysqlFull.parse(str);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new Date();
		}
	}
}
