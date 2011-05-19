package com.jeff.fx.filter.candletype;

public class Bull extends BasicCandleTypeDef
{
    public Bull()
    {
        minSize = 1;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
