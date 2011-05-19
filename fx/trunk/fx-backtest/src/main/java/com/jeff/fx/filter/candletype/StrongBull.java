package com.jeff.fx.filter.candletype;

public class StrongBull extends BasicCandleTypeDef
{
    public StrongBull()
    {
        minSize = 5;
        minRange = 8;
        minBodySize = 0.4;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
