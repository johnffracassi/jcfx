package com.jeff.fx.indicator.line;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.FixedSizeNumberQueue;

public class CommodityChannelIndex extends FixedSizeNumberQueue implements Indicator {

	private static final long serialVersionUID = -3438030867529382721L;

	private CandleValueModel valueModel = CandleValueModel.Typical;
	private MeanAbsoluteDeviation mad;
	private SimpleMovingAverage sma;

	public CommodityChannelIndex(int capacity) {
		super(capacity);
		mad = new MeanAbsoluteDeviation(capacity);
		sma = new SimpleMovingAverage(capacity);
	}

	public void add(CandleDataPoint data) {
		double value = data.evaluate(valueModel);
		add(value);
		mad.add(value);
		sma.add(value);
	}

	@Override
	public double value() {
		double k = (1.0 / 0.015);
		double val = k * ((peek() - sma.value()) / mad.value());
		return val;
	}

	public CandleValueModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(CandleValueModel valueModel) {
		this.valueModel = valueModel;
	}

	@Override
	public String toString() {
		return String.format("CCI(%d) = %.6f (%.6f / %.6f)", capacity(), value(), mad.value(), sma.value());
	}
}
