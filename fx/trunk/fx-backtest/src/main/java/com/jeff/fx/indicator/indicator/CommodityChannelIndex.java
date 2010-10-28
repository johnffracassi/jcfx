package com.jeff.fx.indicator.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class CommodityChannelIndex extends AbstractIndicator
{
    private float scalingFactor = 0.15f;
    
    public CommodityChannelIndex()
    {
        this(14);
    }
    
    public CommodityChannelIndex(int periods)
    {
        super(periods);
    }

    @Override
    public void calculate(CandleCollection candles)
    {
        synchronized (this)
        {
            final int periods = getPeriods();
            final float[] values = new float[candles.getCandleCount()];
            values[0] = Float.NaN;

            SimpleMovingAverage sma = new SimpleMovingAverage(periods, CandleValueModel.Typical);
            sma.calculate(candles);

            // calculate mean deviations
            float[] deviation = new float[values.length];
            deviation[0] = Math.abs(sma.getValue(0) - candles.getPrice(0, CandleValueModel.Typical));
            for (int i = 1, n = candles.getCandleCount(); i < n; i++)
            {
                float lastSma = sma.getValue(i - 1);
                float tp = candles.getPrice(i, CandleValueModel.Typical);
                deviation[i] = Math.abs(lastSma - tp);
            }
            
            float sumDev = 0;
            for (int i = periods, n = candles.getCandleCount(); i < n; i++)
            {
                sumDev = sumDev - deviation[i - periods] + deviation[i];
                float meanDev = sumDev / periods;
                float tp = candles.getPrice(i, CandleValueModel.Typical);
                values[i] = (tp - sma.getValue(i)) / (scalingFactor * meanDev);
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
}
