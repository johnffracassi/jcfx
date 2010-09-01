package com.jeff.fx.indicator;

import com.jeff.fx.common.CandleCollection;

public interface Indicator {

	String getKey();
	String getDisplayName();
	float getValue(int idx);
	void calculate(CandleCollection candles);
	boolean requiresCalculation();
	int getSize();
	void setParams(Object ... params);
	
}
