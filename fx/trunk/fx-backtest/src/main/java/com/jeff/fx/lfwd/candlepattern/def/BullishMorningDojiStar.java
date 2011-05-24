package com.jeff.fx.lfwd.candlepattern.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.MarketSentiment;
import com.jeff.fx.lfwd.candlepattern.CandlePatternDef;

public class BullishMorningDojiStar implements CandlePatternDef
{
    public boolean is(CandleFilterModel model)
    {
        if(model.getIndex() < 2)
            return false;

        CandleDataPoint candle0 = model.getCandles().getCandle(model.getIndex());
        CandleDataPoint candle1 = model.getCandles().getCandle(model.getIndex() - 1);
        CandleDataPoint candle2 = model.getCandles().getCandle(model.getIndex() - 2);


        // bearish, body size > 20, body is 80% of candle
        if(candle2.getSize() > -20)
            return false;
        if(candle2.getBodySize() < candle2.getRange() * 0.80)
            return false;

        // doji-ish, small head, lower low
        if(candle1.getSize() < -4 || candle1.getSize() > 2)
            return false;
        if(candle1.getHeadSize() > 0.20 * candle2.getRange())
            return false;
        if(candle1.getLow() > candle2.getLow())
            return false;

        // higher low
        if(candle0.getLow() > candle1.getLow())
            return false;
        if(candle0.getSize() < 0.40 * candle2.getBodySize())
            return false;
        if(candle0.getClose() < candle1.getHigh())
            return false;

        return true;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.VeryBullish;
    }
}
