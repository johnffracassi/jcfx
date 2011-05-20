package com.jeff.fx.filter.candlepattern.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.filter.CandleFilterModel;
import com.jeff.fx.filter.MarketSentiment;
import com.jeff.fx.filter.candlepattern.CandlePatternDef;

public class BullishMorningDojiStar implements CandlePatternDef
{
    public boolean is(CandleFilterModel model)
    {
        if(model.getIndex() < 2)
            return false;

        CandleDataPoint candle0 = model.getCandles().getCandle(model.getIndex());
        CandleDataPoint candle1 = model.getCandles().getCandle(model.getIndex() - 1);
        CandleDataPoint candle2 = model.getCandles().getCandle(model.getIndex() - 2);

        if(candle2.getSize() > -20)
            return false;

        if(candle1.getSize() > 4 || candle1.getSize() < -4)
            return false;

        if(candle0.getSize() < candle2.getSize() / 2)
            return false;

        return true;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.VeryBullish;
    }
}
