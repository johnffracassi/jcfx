package com.jeff.fx.filter.candletype;

public class WhiteMarubozu extends BasicCandleTypeDef
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
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
