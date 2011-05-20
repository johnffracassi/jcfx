package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class WhiteMarubozu extends AbstractCandleTypeDef
{
    public WhiteMarubozu()
    {
        minRange = 10;
        minSize = 10;
        minBodySize = 0.94;
        maxHeadSize = 0.03;
        maxTailSize = 0.06;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bullish;
    }
}
