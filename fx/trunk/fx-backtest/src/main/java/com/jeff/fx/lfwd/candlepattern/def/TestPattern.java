package com.jeff.fx.lfwd.candlepattern.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.MarketSentiment;
import com.jeff.fx.lfwd.candlepattern.CandlePatternDef;

public class TestPattern implements CandlePatternDef
{
    public boolean is(CandleFilterModel model)
    {
        if(model.getIndex() < 2)
            return false;

        CandleDataPoint candle0 = model.getCandles().getCandle(model.getIndex());
        Indicator atrInd = model.getCache().getIndicator("atr", 14);
        if(atrInd.requiresCalculation())
        {
            atrInd.calculate(model.getCandles());
        }
        double atrVal = atrInd.getValue(0, model.getIndex());
        double range = candle0.getRange();

        return (candle0.getHigh() - candle0.getLow() > atrVal * 5);
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.VeryBullish;
    }
}
