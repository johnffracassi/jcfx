package com.jeff.fx.indicator.overlay;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.FixedSizeNumberQueue2;

@Component
@ChartType(ChartTypes.PriceRelative)
public class SimpleMovingAverage extends AbstractMovingAverage
{
    public SimpleMovingAverage()
    {
        this(14, CandleValueModel.BuyOpen);
    }
    
    public SimpleMovingAverage(int periods, CandleValueModel cvm)
    {
        super(periods, cvm);
    }

    @Override
    public String getKey()
    {
        return "sma";
    }
    
    public void calculate(CandleCollection candles)
    {
        synchronized (this)
        {
            // TODO - use the same averaging method implemented in ATR
            
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
