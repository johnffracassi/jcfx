package com.jeff.fx.indicator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class SimpleMovingAverage implements Indicator {

	private float[] value;
	private int periods;
	private CandleValueModel model;
	private boolean calculated;
	
	public SimpleMovingAverage(int periods, CandleValueModel cvm) {
		this.periods = periods;
		this.model = cvm;
		this.calculated = false;
	}
	
	public void calculate(CandleCollection candles) {
		
		synchronized(this) {
			
			int count = candles.getCandleCount();
			value = new float[count];
			
			for(int idx=periods; idx<count; idx++) {
				float sum = 0.0f;
				for(int i=0; i<periods; i++) {
					sum += candles.getPrice(idx-i, model);
				}
				value[idx] = sum / periods;
			}
			
			calculated = true;
		}
	}

	public final float getSlope(int idx, int countBack) {
		return getValue(idx) - getValue(idx - countBack);
	}
	
	public final int getDirection(int idx) {
		float diff = getSlope(idx, 5);
		return diff > 0.00005 ? 1 : diff < 0.00005 ? -1 : 0;
	}
	
	public final float getValue(int idx) {
		
		if(calculated) {
			return _getValue(idx);
		} else {
			synchronized(this) {
				return _getValue(idx);
			}
		}
	}
	
	private final float _getValue(int idx) {
		if(idx < 0 || idx > value.length) {
			return 0;
		} else {
			return value[idx];
		}
	}
	
	public String getName() {
		return "SMA(" + periods + ")";
	}
	
	public final int hashCode() {
		return getName().hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SimpleMovingAverage) {
			SimpleMovingAverage sma = (SimpleMovingAverage)obj;
			return (sma.periods == periods && sma.model == model);
		}
		return false;
	}
	
	public int getPeriods() {
		return periods;
	}

	public void setPeriods(int periods) {
		this.periods = periods;
	}

	public CandleValueModel getModel() {
		return model;
	}

	public void setModel(CandleValueModel model) {
		this.model = model;
	}

	public boolean requiresCalculation() {
		return !calculated;
	}

	public int getSize() {
		return value.length;
	}
}

