package com.jeff.fx.indicator.overlay;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;

@Component
@ChartType(ChartTypes.PriceRelative)
public class ExponentialMovingAverage extends AbstractMovingAverage
{
    private float multiplier;
    
    public ExponentialMovingAverage()
    {
        this(14, CandleValueModel.BuyOpen);
    }
    
    public ExponentialMovingAverage(int periods, CandleValueModel cvm)
    {
        super(periods, cvm);
        
        multiplier = 2.0f / (periods + 1);
    }

    @Override
    public String getKey()
    {
        return "ema";
    }

    @Override
    public void calculate(CandleCollection candles)
    {
        synchronized (this)
        {
            CandleValueModel model = getModel();
            float[] values = new float[candles.getCandleCount()];
            values[0] = candles.getPrice(0, model);
            
            for (int i = 1, n = candles.getCandleCount(); i < n; i++)
            {
                float price = candles.getPrice(i, model);
                values[i] = (price - values[i-1]) * multiplier + values[i-1];
            }

            setValues(values);
        }
    }
    
}