package com.jeff.fx.indicator;

import java.util.ArrayList;
import java.util.List;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class ZigZagIndicator {
	
	private int windowSize = 40;
	
	public void set(String property, int value) {
		
		boolean triggerUpdate = false;
		
		if("windowSize".equals(property)) {
			windowSize = value;
			triggerUpdate = true;
		}
		
		if(triggerUpdate) {
			// do some shit...
		}
	}
	
	public List<IndicatorMarker> calculate(CandleCollection cc) {
		
		List<IndicatorMarker> idxs = new ArrayList<IndicatorMarker>();
		
		float currentHighVal = Float.MIN_VALUE;
		int countSinceHigh = 0;
		float currentLowVal = Float.MAX_VALUE;
		int countSinceLow = 0;
		int lookingFor = 0;

		// what are we looking for first?
		for(int i=0, n=cc.getCandleCount(); i<n; i++) {
			
			float open = cc.getPrice(i, CandleValueModel.SellOpen);
			if(open == Float.NaN || open == 0.0f) {
				continue;
			}
			
			float low = cc.getPrice(i, CandleValueModel.SellLow);
			if(low < currentLowVal) {
				currentLowVal = low;
				countSinceLow = 0;
			} else {
				countSinceLow ++;
			}
			
			float high = cc.getPrice(i, CandleValueModel.SellHigh);
			if(high > currentHighVal) {
				currentHighVal = high;
				countSinceHigh = 0;
			} else {
				countSinceHigh ++;
			}
			
			if(lookingFor != 1 && countSinceLow == windowSize) {
				lookingFor = 1;
				i = i - windowSize;
				countSinceHigh = 0;
				currentHighVal = cc.getPrice(i, CandleValueModel.High);
				idxs.add(new IndicatorMarker(i, currentLowVal, "Buy", 1));
			} else if(lookingFor != -1 && countSinceHigh == windowSize) {
				lookingFor = -1;
				i = i - windowSize;
				countSinceLow = 0;
				currentLowVal = cc.getPrice(i, CandleValueModel.Low);
				idxs.add(new IndicatorMarker(i, currentHighVal, "Sell", -1));
			}
		}
		
		return idxs;
	}
}
