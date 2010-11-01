package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.FixedSizeNumberQueue2;

@Component
@ChartType(ChartTypes.Oscillator)
public class Stochastic extends AbstractIndicator
{
    private int lookbackPeriods = 14;
    private int smoothingPeriods = 3;
    
    public Stochastic()
    {
        this(14, 3);
    }
    
    public Stochastic(int lookbackPeriods, int smoothingPeriods)
    {
        super(lookbackPeriods);
        this.lookbackPeriods = lookbackPeriods;
        this.smoothingPeriods = smoothingPeriods;
    }

    @Override
    public void calculate(CandleCollection candles)
    {
        synchronized (this)
        {
            FixedSizeNumberQueue2 queue = new FixedSizeNumberQueue2(getPeriods());
            final float[] values = new float[candles.getCandleCount()];

            for (int i = getPeriods(), n = candles.getCandleCount(); i < n; i++)
            {
                values[i] = candles.getPrice(i, CandleValueModel.BuyHigh);
            }

            setValues(values);
        }
    }

    @Override
    public String getKey()
    {
        return "cci";
    }

    @Override
    public void setParams(Object... params)
    {
    }

    public int getLookbackPeriods()
    {
        return lookbackPeriods;
    }

    public void setLookbackPeriods(int lookbackPeriods)
    {
        this.lookbackPeriods = lookbackPeriods;
    }

    public int getSmoothingPeriods()
    {
        return smoothingPeriods;
    }

    public void setSmoothingPeriods(int smoothingPeriods)
    {
        this.smoothingPeriods = smoothingPeriods;
    }
}
