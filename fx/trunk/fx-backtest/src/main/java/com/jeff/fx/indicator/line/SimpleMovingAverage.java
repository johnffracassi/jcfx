package com.jeff.fx.indicator.line;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.FixedSizeNumberQueue;

public class SimpleMovingAverage extends FixedSizeNumberQueue implements
		Indicator {

	private static final long serialVersionUID = -3438030867529382721L;

	private CandleValueModel valueModel = CandleValueModel.AverageOfOHLC;

	public SimpleMovingAverage(int capacity) {
		super(capacity);
	}

	public void add(CandleDataPoint data) {
		add(data.evaluate(valueModel));
	}

	@Override
	public double value() {
		return isFull() ? super.value() : Double.NaN;
	}

	public CandleValueModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(CandleValueModel valueModel) {
		this.valueModel = valueModel;
	}
}
