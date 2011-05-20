package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.MarketSentiment;

public class FourPriceDoji extends Doji
{
    public FourPriceDoji()
    {
    }

    @Override
    public boolean is(CandleDataPoint candle)
    {
        return (candle.getBuyHigh() == candle.getBuyLow());
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Indecision;
    }
}
