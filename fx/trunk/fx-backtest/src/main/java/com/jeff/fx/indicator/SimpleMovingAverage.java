package com.jeff.fx.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

@Component
@ChartType(ChartTypes.PriceRelative)
public class SimpleMovingAverage extends AbstractMovingAverage
{
    public SimpleMovingAverage(int periods, CandleValueModel cvm)
    {
        super(periods, cvm);
    }

    public void calculate(CandleCollection candles)
    {
        synchronized (this)
        {
            FixedSizeNumberQueue2 q = new FixedSizeNumberQueue2(getPeriods());
            CandleValueModel model = getModel();
            float[] values = new float[candles.getCandleCount()];

            for (int i = 0, n = candles.getCandleCount(); i < n; i++)
            {
                q.add(candles.getPrice(i, model));
                values[i] = q.average();
            }

            setValues(values);
        }
    }
}
