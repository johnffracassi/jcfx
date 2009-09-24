package com.jeff.fx.indicator.line;

public class ExponentialMovingAverage extends SimpleMovingAverage implements
		Indicator {

	private static final long serialVersionUID = -1710408609792814518L;

	private final double smoothing;

	public ExponentialMovingAverage(int capacity, double smoothing) {
		super(capacity);
		this.smoothing = smoothing;
	}

	public ExponentialMovingAverage(int capacity) {
		this(capacity, (1.0 / (double) capacity));
	}

	@Override
	public double value() {
		Double[] values = toArray(new Double[size()]);

		double sumOfValues = 0.0;
		double sumOfWeights = 0.0;

		double weight = 100.0;

		for (int i = values.length - 1; i >= 0; i--) {
			sumOfValues += weight * values[i];
			sumOfWeights += weight;
			weight *= (1.0 - smoothing);
		}

		return (sumOfValues / sumOfWeights);
	}
}
