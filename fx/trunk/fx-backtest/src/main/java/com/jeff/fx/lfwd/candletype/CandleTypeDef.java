package com.jeff.fx.lfwd.candletype;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.MarketSentiment;

public interface CandleTypeDef
{
    boolean is(CandleDataPoint candle);
    MarketSentiment sentiment();
}
