package com.jeff.fx.lfwd.candlepattern.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.MarketSentiment;
import com.jeff.fx.lfwd.candlepattern.CandlePatternDef;

public class BearishEveningDojiStar implements CandlePatternDef
{
    public boolean is(CandleFilterModel model)
    {
        if(model.getIndex() < 2)
            return false;

        CandleDataPoint c0 = model.getCandles().getCandle(model.getIndex());
        CandleDataPoint c1 = model.getCandles().getCandle(model.getIndex() - 1);
        CandleDataPoint c2 = model.getCandles().getCandle(model.getIndex() - 2);

        boolean[] conditions = {
            c2.getLow() < c1.getLow(),
            c1.getLow() > c0.getLow(),
            c2.getHigh() < c1.getHigh(),
            c1.getHigh() > c0.getHigh(),
            c1.getLow() > c2.getOpen(),
            c1.getLow() < c0.getClose(),
            c2.getSize() >= 10,
            c1.getBodySize() <= 5,
            c0.getSize() <= -10

        };

        for(boolean b : conditions)
        {
            if(!b)
                return false;
        }
        return true;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.VeryBearish;
    }
}
