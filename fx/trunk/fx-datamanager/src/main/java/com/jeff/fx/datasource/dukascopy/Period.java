package com.jeff.fx.datasource.dukascopy;

import com.jeff.fx.common.TimeUnit;


public enum Period 
{
    Tick(null, -1, "t"),
    TenSecond(TimeUnit.Second, 10, "s10"),
    OneMin(TimeUnit.Minute, 1, "m1"),
    FiveMin(TimeUnit.Minute, 5, "m5"),
    TenMin(TimeUnit.Minute, 10, "m10"),
    FifteenMin(TimeUnit.Minute, 15, "m15"),
    ThirtyMin(TimeUnit.Minute, 30, "m30"),
    OneHour(TimeUnit.Hour, 1, "h1"),
    FourHour(TimeUnit.Hour, 4, "h4"),
    Day(TimeUnit.Day, 1, "day"),
    Week(TimeUnit.Week, 1, "week"),
    Month(TimeUnit.Month, 1, "month");

    public final TimeUnit unit;
    public final int numOfUnits;
    public final String key;

    private Period(TimeUnit unit, int nUnits, String display) 
    {
        this.unit = unit;
        this.numOfUnits = nUnits;
        this.key = display;
    }

    public final long getInterval() 
    {
        return this == Tick ? -1 : unit.getInterval() * numOfUnits;
    }
}
