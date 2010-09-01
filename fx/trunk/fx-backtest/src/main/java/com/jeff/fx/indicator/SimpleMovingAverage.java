package com.jeff.fx.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

@Component
@ChartType(ChartTypes.PriceRelative)
public class SimpleMovingAverage implements Indicator {

	private float[] value;
	private boolean calculated;
	
	@Property(key="sma.periods")
	@ValidationRange(min=0, max=1000)
	@Label("Periods")
	private Integer periods;
	
	@Property(key="sma.cvm")
	@Label("Price Model")
	private CandleValueModel model;

	public SimpleMovingAverage() {
		this(14, CandleValueModel.Typical);
	}
	
	public SimpleMovingAverage(int periods, CandleValueModel cvm) {
		this.periods = periods;
		this.model = cvm;
		this.calculated = false;
	}

	public void setParams(Object ... params) {
		periods = new Integer(String.valueOf(params[0]));
		model = CandleValueModel.valueOf(String.valueOf(params[1]));
	}
	
	public void calculate(CandleCollection candles) {
		
		synchronized(this) {
			
			FixedSizeNumberQueue q = new FixedSizeNumberQueue(periods);
			value = new float[candles.getCandleCount()];
			
			for(int i=0, n=candles.getCandleCount(); i<n; i++) {
				q.add(candles.getPrice(i, model));
				value[i] = q.average();
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
	
	public String getKey() {
		return "sma";
	}
	
	public String getDisplayName() {
		return "SMA(" + periods + "," + model + ")";
	}
	
	public final int hashCode() {
		return getDisplayName().hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SimpleMovingAverage) {
			SimpleMovingAverage sma = (SimpleMovingAverage)obj;
			return (sma.periods == periods && sma.model == model);
		}
		return false;
	}
	
	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
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
	
	public String toString() {
		return getDisplayName();
	}
}

