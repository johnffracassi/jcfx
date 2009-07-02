package com.siebentag.fx.source;

public enum Unit 
{
    Second(1000L, "s", "Sec", "Second"),
    Minute(60 * 1000L, "m", "Min", "Minute"),
    Hour(60 * 60 * 1000L, "h", "Hour", "Hourly"),
    Day(24 * 60 * 60 * 1000L, "D", "Day", "Daily"),
    Week(7 * 24 * 60 * 60 * 1000L, "W", "Week", "Weekly"),
    Month(30 * 24 * 60 * 60 * 1000L, "M", "Month", "Monthly");

    private long interval;

    private Unit(long interval, String shortDescription, String compactDescription, String longDescription) 
    {
        this.interval = interval;
    }
    
    public final long getInterval() 
    {
        return interval;
    }
}