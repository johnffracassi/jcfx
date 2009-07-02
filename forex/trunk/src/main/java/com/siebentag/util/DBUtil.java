package com.siebentag.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtil
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
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
}
