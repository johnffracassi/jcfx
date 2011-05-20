package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

public class Doji extends BasicCandleTypeDef
{
    public Doji()
    {
        minRange = 10;
        maxSize = 2;
        minSize = -2;
        minHeadSize = 0.10;
        minTailSize = 0.10;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Indecision;
    }
}
