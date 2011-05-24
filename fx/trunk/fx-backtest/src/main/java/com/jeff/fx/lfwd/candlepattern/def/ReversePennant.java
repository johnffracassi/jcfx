package com.jeff.fx.lfwd.candlepattern.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.MarketSentiment;
import com.jeff.fx.lfwd.candlepattern.CandlePatternDef;

public class ReversePennant implements CandlePatternDef
{
    public boolean is(CandleFilterModel model)
    {
        int points = 7;

        if(model.getIndex() < points)
            return false;

        CandleDataPoint[] c = new CandleDataPoint[points];
        for(int i=0; i<points; i++)
        {
            c[i] = model.getCandles().getCandle(model.getIndex() - i);

            if(i == 0 && c[0].getSize() <= 0)
            {
                return false;
            }
            
            if(i > 0 && c[i].getSize() > c[i-1].getSize())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.VeryBearish;
    }
}
