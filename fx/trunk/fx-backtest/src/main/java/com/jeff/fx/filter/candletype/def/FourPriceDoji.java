package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.filter.MarketSentiment;

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
