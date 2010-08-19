package com.jeff.fx.common;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeOfWeek {
	
	private static final String[] DAY = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
	
	private int dayOfWeek;
	private LocalTime time;
	
	public TimeOfWeek() {
		this(0, LocalTime.MIDNIGHT);
	}
	
	public TimeOfWeek(int minuteOfWeek) {
		minuteOfWeek %= 10080; 
		dayOfWeek = minuteOfWeek / 1440;
		int minOfDay = minuteOfWeek % 1440;
		time = LocalTime.MIDNIGHT.plusMinutes(minOfDay);
	}
	
	public TimeOfWeek(int dayOfWeek, LocalTime time) {
		this.dayOfWeek = dayOfWeek % 7;
		this.time = time;
	}

	public TimeOfWeek(int dayOfWeek, int hour, int minute) {
		this(dayOfWeek, new LocalTime(hour, minute));
	}
	
	public TimeOfWeek(LocalDateTime dateTime) {
		this.dayOfWeek = dateTime.getDayOfWeek() % 7;
		this.time = dateTime.toLocalTime();
	}
	
	public int periodOfWeek(Period period) {
		return (int)(getMinuteOfWeek() / (period.getInterval() / 60000));
	}
	
	public boolean isAfter(TimeOfWeek other) {
		if(dayOfWeek < other.dayOfWeek) return false;
		if(dayOfWeek > other.dayOfWeek) return true;
		return time.isAfter(other.time) || time.isEqual(other.time);
	}

	public boolean isBefore(TimeOfWeek other) {
		if(dayOfWeek < other.dayOfWeek) return true;
		if(dayOfWeek > other.dayOfWeek) return false;
		return time.isBefore(other.time);
	}
	
	public String toString() {
		return DAY[dayOfWeek] + " " + dtf.print(time);
	}
	
	public int getMinuteOfWeek() {
		return 1440 * dayOfWeek + (Minutes.minutesBetween(LocalTime.MIDNIGHT, time).getMinutes());
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	
	public LocalTime getTime() {
		return time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dayOfWeek;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		TimeOfWeek other = (TimeOfWeek) obj;

		if (dayOfWeek != other.dayOfWeek)
			return false;
		
		if(time == null || other.time == null)
			return false;
		
		return time.equals(other.time);
	}
}
