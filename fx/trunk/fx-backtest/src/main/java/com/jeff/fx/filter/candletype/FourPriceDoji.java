package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

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
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Indecision;
    }
}
