package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class Hammer extends HangingMan
{
    public Hammer()
    {
        minSize = 0.0;
        maxSize = Double.POSITIVE_INFINITY;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bullish;
    }
}
