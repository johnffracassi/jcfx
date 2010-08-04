package com.jeff.fx.common;

public enum TimeUnit 
{
    Second(1000l, "s", "Sec", "Second"),
    Minute(Second.interval * 60, "m", "Min", "Minute"),
    Hour(Minute.interval * 60, "h", "Hour", "Hourly"),
    Day(Hour.interval * 24, "D", "Day", "Daily"),
    Week(Day.interval * 7, "W", "Week", "Weekly"),
    Month(Day.interval * 30, "M", "Month", "Monthly");

    // interval is in milliseconds
    private long interval;

    private TimeUnit(long interval, String shortDescription, String compactDescription, String longDescription) {
        this.interval = interval;
    }
    
    public final long getInterval() {
        return interval;
    }
}