package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class Bull extends AbstractCandleTypeDef
{
    public Bull()
    {
        minSize = 4;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bullish;
    }
}
