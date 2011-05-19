package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

public interface CandleTypeDef
{
    boolean is(CandleDataPoint candle);
    CandleSentiment sentiment();
}
