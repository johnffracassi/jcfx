package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.FixedSizeNumberQueue2;

@Component
@ChartType(ChartTypes.Oscillator)
public class CommodityChannelIndex extends AbstractIndicator
{
    private float scalingFactor = 0.015f;
    
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
            FixedSizeNumberQueue2 queue = new FixedSizeNumberQueue2(getPeriods());
            final float[] values = new float[candles.getCandleCount()];

            for (int i = getPeriods(), n = candles.getCandleCount(); i < n; i++)
            {
                float price = candles.getPrice(i, CandleValueModel.Typical);
                queue.add(price);
                values[i] = (float) ((price - queue.average()) / (scalingFactor * queue.meanDeviation()));
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
