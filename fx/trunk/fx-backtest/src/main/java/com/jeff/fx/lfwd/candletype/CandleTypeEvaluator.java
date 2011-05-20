package com.jeff.fx.lfwd.candletype;

import com.jeff.fx.common.CandleDataPoint;

interface CandleTypeEvaluator
{
	boolean evaluate(CandleDataPoint dp);
}
