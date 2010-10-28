package com.jeff.fx.indicator.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.Label;
import com.jeff.fx.indicator.Property;
import com.jeff.fx.indicator.ValidationRange;

public abstract class AbstractIndicator implements Indicator
{
    private float[] values;
    private boolean calculated;

    @Property(key = "atr.periods")
    @ValidationRange(min = 0, max = 1000)
    @Label("Periods")
    private Integer periods;

    public abstract String getKey();
    public abstract void setParams(Object... params);
    public abstract void calculate(CandleCollection candles);

    public AbstractIndicator(int periods)
    {
        this.periods = periods;
    }
    
    public final float getValue(int idx)
    {
        if (calculated)
        {
            return _getValue(idx);
        }
        else
        {
            synchronized (this)
            {
                return _getValue(idx);
            }
        }
    }

    private final float _getValue(int idx)
    {
        if (idx < 0 || idx > values.length)
        {
            return 0;
        }
        else
        {
            return values[idx];
        }
    }

    public String getDisplayName()
    {
        return getKey() + "(" + periods + ")";
    }

    public final int hashCode()
    {
        return getDisplayName().hashCode();
    }

    public boolean equals(Object obj)
    {
        if (obj.getClass().equals(getClass()))
        {
            AbstractIndicator ind = (AbstractIndicator) obj;
            return (ind.periods == periods);
        }
        
        return false;
    }

    public Integer getPeriods()
    {
        return periods;
    }

    public void setPeriods(Integer periods)
    {
        this.periods = periods;
    }

    protected void setValues(float[] values)
    {
        this.values = values;
        this.calculated = true;
    }

    public void invalidate()
    {
        this.calculated = false;
    }

    public boolean requiresCalculation()
    {
        return !calculated;
    }

    public int getSize()
    {
        return values.length;
    }

    public String toString()
    {
        return getDisplayName();
    }
}
