package com.jeff.fx.util;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.jeff.fx.common.Period;

public class DateUtil
{
	private static DateTimeFormatter shortDateFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	private static DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("hh:mm");
	private static DateTimeFormatter hourDf = DateTimeFormat.forPattern("H");
	
	public static String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	public static String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	
	public static String format(LocalDateTime date) {
		return shortDateFormatter.print(date);
	}
	
	public static String formatDate(LocalDateTime date) {
		return shortDateFormatter.print(date);
	}
	
	public static String formatTime(LocalDateTime date) {
		return timeFormatter.print(date);
	}
	
	public static String formatHour(LocalDateTime date) {
		return hourDf.print(date) + "h";
	}
	
	public static LocalDate getStartOfWeek(LocalDate date) {
		if(date.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			return date;
		} else {
			return date.minusDays(date.getDayOfWeek());
		}
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

	public static String getDayOfWeek(int dayOfWeek) {
		return DAYS[dayOfWeek % 7];
	}
}
