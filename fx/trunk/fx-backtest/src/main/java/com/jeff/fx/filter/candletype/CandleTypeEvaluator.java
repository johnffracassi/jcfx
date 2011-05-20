package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

interface CandleTypeEvaluator
{
	boolean evaluate(CandleDataPoint dp);
}
