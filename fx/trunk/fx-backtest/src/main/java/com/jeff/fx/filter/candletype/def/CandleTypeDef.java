package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.filter.MarketSentiment;

interface CandleTypeDef
{
    boolean is(CandleDataPoint candle);
    MarketSentiment sentiment();
}
