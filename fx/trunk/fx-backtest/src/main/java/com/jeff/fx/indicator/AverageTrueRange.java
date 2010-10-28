package com.jeff.fx.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class AverageTrueRange implements Indicator
{
    private float[] values;
    private boolean calculated;

    @Property(key = "atr.periods")
    @ValidationRange(min = 0, max = 1000)
    @Label("Periods")
    private Integer periods;

    public AverageTrueRange()
    {
        this(14, CandleValueModel.Typical);
    }

    public AverageTrueRange(int periods, CandleValueModel cvm)
    {
        this.periods = periods;
        this.calculated = false;
    }

    public void calculate(CandleCollection candles)
    {
        synchronized (this)
        {
            float[] values = new float[candles.getCandleCount()];
            values[0] = candles.getPrice(0, CandleValueModel.BuyHigh) - candles.getPrice(0, CandleValueModel.BuyLow);
            
            for (int i = 1, n = candles.getCandleCount(); i < n; i++)
            {
                float high = candles.getPrice(i, CandleValueModel.BuyHigh);
                float low = candles.getPrice(i, CandleValueModel.BuyLow);
                float closePrevious = candles.getPrice(i-1, CandleValueModel.BuyClose);
                float trueRange = calculateTrueRange(high, low, closePrevious);
                
                values[i] = ((values[i-1] * (periods - 1)) + trueRange) / periods;
            }

            setValues(values);
        }
    }

    private float calculateTrueRange(float high, float low, float closePrev)
    {
        float hl = high - low;
        float hcp = Math.abs(high - closePrev);
        float lcp = Math.abs(low - closePrev);
        return Math.max(hl, Math.max(hcp, lcp));
    }

    public String getKey()
    {
        return "atr";
    }

    public void setParams(Object... params)
    {
        periods = new Integer(String.valueOf(params[0]));
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
        if (obj.getClass().equals(getClass()) && this instanceof AverageTrueRange)
        {
            AverageTrueRange ind = (AverageTrueRange) obj;
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
