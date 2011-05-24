package com.jeff.fx.lfwd.candlepattern.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.MarketSentiment;
import com.jeff.fx.lfwd.candlepattern.CandlePatternDef;

public class Pennant implements CandlePatternDef
{
    public boolean is(CandleFilterModel model)
    {
        if(model.getIndex() < 5)
            return false;

        CandleDataPoint[] c = new CandleDataPoint[5];
        for(int i=0; i<5; i++)
        {
            c[i] = model.getCandles().getCandle(model.getIndex() - i);
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
