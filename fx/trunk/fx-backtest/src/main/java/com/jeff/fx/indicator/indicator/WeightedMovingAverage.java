package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.FixedSizeNumberQueue2;

@Component
@ChartType(ChartTypes.PriceRelative)
public class WeightedMovingAverage extends AbstractMovingAverage
{
    private final float[] weights;
    
    public WeightedMovingAverage(int periods, CandleValueModel cvm)
    {
        super(periods, cvm);
        weights = buildWeights(periods);
    }

    @Override
    public String getKey()
    {
        return "wma";
    }

    public float[] buildWeights(int periods)
    {
    	float[] weights = new float[periods];
        float step = (1.0f / periods);
        for(int i=0; i<periods; i++)
        {
            weights[i] = 1.0f - (i * step);
        }
        return weights;
    }
    
    @Override
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
                values[i] = q.weightedAverage(weights);
            }

            setValues(values);
        }
    }
}