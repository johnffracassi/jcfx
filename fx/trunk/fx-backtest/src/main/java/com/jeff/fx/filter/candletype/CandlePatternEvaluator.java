package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

interface CandlePatternEvaluator
{
	boolean evaluate(CandleDataPoint dp);
}
