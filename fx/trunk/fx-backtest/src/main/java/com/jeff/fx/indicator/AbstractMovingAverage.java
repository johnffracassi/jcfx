package com.jeff.fx.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public abstract class AbstractMovingAverage implements Indicator
{
    private float[] values;
    private boolean calculated;

    @Property(key = "ma.periods")
    @ValidationRange(min = 0, max = 1000)
    @Label("Periods")
    private Integer periods;

    @Property(key = "ma.cvm")
    @Label("Price Model")
    private CandleValueModel model;

    public AbstractMovingAverage()
    {
        this(14, CandleValueModel.Typical);
    }

    public AbstractMovingAverage(int periods, CandleValueModel cvm)
    {
        this.periods = periods;
        this.model = cvm;
        this.calculated = false;
    }

    public void setParams(Object... params)
    {
        periods = new Integer(String.valueOf(params[0]));
        model = CandleValueModel.valueOf(String.valueOf(params[1]));
    }

    public abstract void calculate(CandleCollection candles);

    public final float getSlope(int idx, int countBack)
    {
        return getValue(idx) - getValue(idx - countBack);
    }

    public final int getDirection(int idx)
    {
        float diff = getSlope(idx, 5);
        return diff > 0.00005 ? 1 : diff < 0.00005 ? -1 : 0;
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

    public String getKey()
    {
        return "sma";
    }

    public String getDisplayName()
    {
        return "SMA(" + periods + "," + model + ")";
    }

    public final int hashCode()
    {
        return getDisplayName().hashCode();
    }

    public boolean equals(Object obj)
    {
        if (obj.getClass().equals(getClass()) && this instanceof AbstractMovingAverage)
        {
            AbstractMovingAverage ma = (AbstractMovingAverage) obj;
            return (ma.periods == periods && ma.model == model);
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

    public CandleValueModel getModel()
    {
        return model;
    }

    public void setModel(CandleValueModel model)
    {
        this.model = model;
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
