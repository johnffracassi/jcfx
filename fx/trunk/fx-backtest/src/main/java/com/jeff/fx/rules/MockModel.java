package com.jeff.fx.rules;

import com.jeff.fx.common.Period;

public class MockModel
{
    private int number = 43;
    private Period period = Period.ThirtyMin;

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Period getPeriod()
    {
        return period;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }
}