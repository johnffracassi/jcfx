package com.jeff.fx.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DBDateUtil
{
	private static final DateTimeFormatter sdf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

	private static DateTimeFormatter mysqlTime = DateTimeFormat.forPattern("HH:mm:ss");
	private static DateTimeFormatter mysqlDate = DateTimeFormat.forPattern("yyyy-MM-dd");
	private static DateTimeFormatter mysqlFull = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static LocalDateTime toDate(java.sql.Date date, java.sql.Time time, int milliseconds) throws ParseException
	{
		 return sdf.parseDateTime(date + " " + time + "." + milliseconds).toLocalDateTime();
	}

	public static LocalDateTime toDate(java.sql.Date date, java.sql.Time time) throws ParseException
	{
		 return toDate(date, time, 0);
	}
	
	public static LocalDateTime fromMySQL(Date date, Time time, int milliseconds)
	{
		LocalDateTime ldt = new LocalDateTime(date.getTime());
		ldt = ldt.withMillisOfDay((int)(time.getTime() + milliseconds));
		return ldt;
	}

	public static LocalDateTime fromMySQL(Timestamp timestamp)
	{
		return new LocalDateTime(timestamp.getTime());
	}	
	
	public static String formatMySQLTime(LocalDateTime date)
	{
		return mysqlTime.print(date);
	}
	
	public static String formatMySQLDate(LocalDateTime date)
	{
		return mysqlDate.print(date);
	}	
	
	public static String formatMySQLDateTime(LocalDateTime dateTime)
	{
		return mysqlFull.print(dateTime);
	}
}
