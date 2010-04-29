package com.jeff.fx.indicator.line;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.FixedSizeNumberQueue;

public class MeanAbsoluteDeviation extends FixedSizeNumberQueue implements Indicator {

	private static final long serialVersionUID = -3438030867529382721L;

	private CandleValueModel valueModel = CandleValueModel.Typical;

	public MeanAbsoluteDeviation(int capacity) {
		super(capacity);
	}

	public void add(CandleDataPoint data) {
		add(data.evaluate(valueModel));
	}

	@Override
	public double value() {
		
		double mean = average();
		
		Double[] values = toArray(new Double[size()]);
		
		double deviationTotal = 0.0;
		for(int i=0; i<size(); i++) {
			double deviation = Math.abs(values[i] - mean);
			deviationTotal += deviation;
		}
		
		return isFull() ? (deviationTotal / size()) : Double.NaN;
	}

	public CandleValueModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(CandleValueModel valueModel) {
		this.valueModel = valueModel;
	}

	@Override
	public String toString() {
		return String.format("MAD(%d) = %.6f", capacity(), value());
	}
}
