package com.jeff.fx.common;


public enum Period 
{
    Tick(null, -1),
    TenSecond(TimeUnit.Second, 10),
    OneMin(TimeUnit.Minute, 1),
    FiveMin(TimeUnit.Minute, 5),
    TenMin(TimeUnit.Minute, 10),
    FifteenMin(TimeUnit.Minute, 15),
    ThirtyMin(TimeUnit.Minute, 30),
    OneHour(TimeUnit.Hour, 1),
    FourHour(TimeUnit.Hour, 4),
    Day(TimeUnit.Day, 1),
    Week(TimeUnit.Week, 1),
    Month(TimeUnit.Month, 1);

    public final TimeUnit unit;
    public final int numOfUnits;

    private Period(TimeUnit unit, int nUnits) 
    {
        this.unit = unit;
        this.numOfUnits = nUnits;
    }

    public final long getInterval() 
    {
        return this == Tick ? -1 : unit.getInterval() * numOfUnits;
    }
}
