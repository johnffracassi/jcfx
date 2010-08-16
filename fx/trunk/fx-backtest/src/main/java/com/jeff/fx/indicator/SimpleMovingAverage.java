package com.jeff.fx.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class SimpleMovingAverage {

	private float[] value;
	private final int periods;
	private final CandleValueModel model;
	
	public SimpleMovingAverage(int period, CandleValueModel model) {
		this.periods = period;
		this.model = model;
	}
	
	public void calculate(CandleCollection candles) {
		
		int count = candles.getCandleCount();
		value = new float[count];
		
		for(int idx=periods; idx<count; idx++) {
			float sum = 0.0f;
			for(int i=0; i<periods; i++) {
				sum += candles.getPrice(idx-i, model);
			}
			value[idx] = sum / periods;
		}
	}

	public float getValue(int idx) {
		if(idx < 0 || idx > value.length) {
			return 0;
		} else {
			return value[idx];
		}
	}
	
	public int getLength() {
		return value.length;
	}
}

