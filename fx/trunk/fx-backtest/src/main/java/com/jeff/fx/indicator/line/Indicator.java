package com.jeff.fx.indicator.line;

import com.jeff.fx.common.CandleDataPoint;

public interface Indicator {
	void add(CandleDataPoint data);

	double value();
}
