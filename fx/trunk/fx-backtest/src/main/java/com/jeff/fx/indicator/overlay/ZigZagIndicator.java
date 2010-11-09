package com.jeff.fx.indicator.overlay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.AbstractIndicator;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.IndicatorMarker;
import com.jeff.fx.indicator.Label;
import com.jeff.fx.indicator.Property;
import com.jeff.fx.indicator.ValidationRange;

@Component
@ChartType(ChartTypes.Annotated)
public class ZigZagIndicator extends AbstractIndicator {
	
	private Map<Integer,IndicatorMarker> idxs;
	private boolean calculated = false;
	private CandleCollection candles;

	@Property(key="zz.windowSize")
	@ValidationRange(min=0,max=250)
	@Label("Window Size")
	private int windowSize = 40;
	
	public ZigZagIndicator() {
		this(40);
	}
	
	public ZigZagIndicator(int windowSize) {
	    super(windowSize);
		this.windowSize = windowSize;
	}
	
	public void setParams(Object ... params) {
		this.windowSize = new Integer(String.valueOf(params[0]));
	}
	
	public void set(String property, int value) {
		
		boolean triggerUpdate = false;
		
		if("windowSize".equals(property)) {
			windowSize = value;
			triggerUpdate = true;
		}
		
		if(triggerUpdate) {
			recalculate();
		}
	}
	
	public void recalculate() {
		calculate(candles);
	}
	
	public void calculate(CandleCollection cc) {
		
		calculated = false;
		candles = cc;
		
		idxs = new HashMap<Integer,IndicatorMarker>();
		
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
				idxs.put(i, new IndicatorMarker(i, currentLowVal, "Buy", 1));
			} else if(lookingFor != -1 && countSinceHigh == windowSize) {
				lookingFor = -1;
				i = i - windowSize;
				countSinceLow = 0;
				currentLowVal = cc.getPrice(i, CandleValueModel.Low);
				idxs.put(i, new IndicatorMarker(i, currentHighVal, "Sell", -1));
			}
		}
		
		calculated = true;
	}

	public String getKey() {
		return "zzi";
	}
	
	public String getDisplayName() {
		return "ZigZag";
	}

	public boolean requiresCalculation() {
		return !calculated;
	}

	public int getSize() {
		return idxs.size();
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

    @Override
    public void invalidate()
    {
        calculated = false;
    }
}
