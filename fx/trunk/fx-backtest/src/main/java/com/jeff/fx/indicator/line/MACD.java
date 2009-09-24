package com.jeff.fx.indicator.line;

import com.jeff.fx.common.CandleDataPoint;

public class MACD implements Indicator {

	private ExponentialMovingAverage emaShort;
	private ExponentialMovingAverage emaLong;

	public MACD() {
		this(12, 26);
	}

	public MACD(int ema1, int ema2) {
		emaShort = new ExponentialMovingAverage(ema1);
		emaLong = new ExponentialMovingAverage(ema2);
	}

	public void add(CandleDataPoint val) {
		emaShort.add(val);
		emaLong.add(val);
	}

	public double value() {
		return emaShort.value() - emaLong.value();
	}
}
