package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class Bear extends AbstractCandleTypeDef
{
    public Bear()
    {
        maxSize = -4;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bearish;
    }
}
