package com.jeff.fx.indicator.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class AverageTrueRange extends AbstractIndicator
{
    public AverageTrueRange()
    {
        this(14);
    }

    public AverageTrueRange(int periods)
    {
        super(periods);
    }

    public void calculate(final CandleCollection candles)
    {
        synchronized (this)
        {
            final int periods = getPeriods();
            final float[] values = new float[candles.getCandleCount()];
            values[0] = candles.getPrice(0, CandleValueModel.BuyHigh) - candles.getPrice(0, CandleValueModel.BuyLow);
            
            for (int i = 1, n = candles.getCandleCount(); i < n; i++)
            {
                final float high = candles.getPrice(i, CandleValueModel.BuyHigh);
                final float low = candles.getPrice(i, CandleValueModel.BuyLow);
                final float closePrevious = candles.getPrice(i-1, CandleValueModel.BuyClose);
                final float trueRange = calculateTrueRange(high, low, closePrevious);
                
                values[i] = ((values[i-1] * (periods - 1)) + trueRange) / periods;
            }

            setValues(values);
        }
    }

    private float calculateTrueRange(final float high, final float low, final float closePrev)
    {
        float hl = high - low;
        float hcp = Math.abs(high - closePrev);
        float lcp = Math.abs(low - closePrev);
        return Math.max(hl, Math.max(hcp, lcp));
    }

    public void setParams(Object... params)
    {
        setPeriods(new Integer(String.valueOf(params[0])));
    }

    @Override
    public String getKey()
    {
        return "atr";
    }

}
