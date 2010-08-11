package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.common.OfferSide;

public class OPOfferSide extends OptimiserParameter<OfferSide,Integer> {

	public OPOfferSide(String key) {
		super(key, OfferSide.Ask, OfferSide.Bid, 1);
	}

	public OfferSide fromString(String val) {
		return OfferSide.valueOf(val);
	}

	public int getStepCount() {
		return 2;
	}

	public OfferSide getValue(int step) {
		return OfferSide.values()[step];
	}
}

