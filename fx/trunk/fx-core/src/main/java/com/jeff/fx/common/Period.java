package com.jeff.fx.common;


public enum Period 
{
    TICK(null, -1),
    TEN_SECS(Unit.Second, 10),
    ONE_MIN(Unit.Minute, 1),
    FIVE_MINS(Unit.Minute, 5),
    TEN_MINS(Unit.Minute, 10),
    FIFTEEN_MINS(Unit.Minute, 15),
    THIRTY_MINS(Unit.Minute, 30),
    ONE_HOUR(Unit.Hour, 1),
    FOUR_HOURS(Unit.Hour, 4),
    DAILY(Unit.Day, 1),
    WEEKLY(Unit.Week, 1),
    MONTHLY(Unit.Month, 1);

    public final Unit unit;
    public final int numOfUnits;

    private Period(Unit unit, int nUnits) 
    {
        this.unit = unit;
        this.numOfUnits = nUnits;
    }

    public final long getInterval() 
    {
        return this == TICK ? -1 : unit.getInterval() * numOfUnits;
    }
}
