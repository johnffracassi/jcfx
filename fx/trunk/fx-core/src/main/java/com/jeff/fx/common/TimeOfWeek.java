package com.jeff.fx.common;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

public class TimeOfWeek implements Comparable<TimeOfWeek>, Serializable
{
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    public static final String[] SHORT_DAY = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    public static final String[] DAY = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static final String[] FULL_DAY = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    
    private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
    private static final DateTimeFormatter shortFormat = DateTimeFormat.forPattern("HHmm");
	
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
		this(dayOfWeek, new LocalTime(hour, minute, 00));
	}
	
	public TimeOfWeek(LocalDateTime dateTime) {
		this.dayOfWeek = dateTime.getDayOfWeek() % 7;
		this.time = dateTime.toLocalTime();
	}

    public TimeOfWeek(String str)
    {
        this(toDayOfWeek(str.substring(0, 2)), new Integer(str.substring(2, 4)), new Integer(str.substring(4)));
    }

    public static int toDayOfWeek(String str)
    {
        String[] dayArray;

        if(str.length() == 2)
            dayArray = SHORT_DAY;
        else if(str.length() == 3)
            dayArray = DAY;
        else
            dayArray = FULL_DAY;

        for(int i=0; i<dayArray.length; i++)
        {
            if(dayArray[i].equals(str))
            {
                return i;
            }
        }

        throw new RuntimeException("Could not map day '" + str + "' to index");
	}

    public int periodOfWeek(Period period) {
		long periodInMillis = period.getInterval();
		if(periodInMillis < 60000) {
			periodInMillis = 60000;
		}
		return (int)(getMinuteOfWeek() / (periodInMillis / 60000));
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

    public boolean isAfterOrEqualTo(TimeOfWeek start) {
        return (isAfter(start) || equals(start));
    }

    public boolean isBeforeOrEqualTo(TimeOfWeek start) {
        return (isBefore(start) || equals(start));
    }

	public LocalDateTime forWeekContaining(LocalDate date) {
		return forWeekContaining(date.toLocalDateTime(LocalTime.MIDNIGHT));
	}
	
	public LocalDateTime forWeekContaining(LocalDateTime ldt) {
		return ldt.minusDays(ldt.getDayOfWeek() % 7).withTime(0, 0, 0, 0).plusMinutes(getMinuteOfWeek());
	}
	
	public String toString() {
		return SHORT_DAY[dayOfWeek] + shortFormat.print(time);
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

        TimeOfWeek other;
        if(obj instanceof String)
            other = new TimeOfWeek((String)obj);
        else
            other = (TimeOfWeek) obj;

		if (dayOfWeek != other.dayOfWeek)
			return false;
		
		if(time == null || other.time == null)
			return false;
		
		return time.equals(other.time);
	}

    @Override
    public int compareTo(TimeOfWeek other)
    {
        if(dayOfWeek < other.dayOfWeek)
            return -1;
        
        if(dayOfWeek > other.dayOfWeek)
            return 1;
        
        if(time.isBefore(other.time))
            return -1;
        
        if(time.isAfter(other.time))
            return 1;
        
        return 0;
    }
}
