package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.MarketSentiment;

interface CandleTypeDef
{
    boolean is(CandleDataPoint candle);
    MarketSentiment sentiment();
}
