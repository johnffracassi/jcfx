package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

public class DragonFlyDoji extends BasicCandleTypeDef
{
    public DragonFlyDoji()
    {
        minRange = 10;
        minSize = 0;
        maxBodySize = 0.05;
        maxHeadSize = 0.05;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
