package com.jeff.fx.common;

public enum CandleValueModel {

	AverageOfHL(new Evaluator() {
		public double evaluate(CandleDataPoint dp) {
			return (dp.getBuyHigh() + dp.getBuyLow() + dp.getSellHigh() + dp
					.getSellLow()) / 4.0;
		};
	}),

	AverageOfOHLC(new Evaluator() {
		public double evaluate(CandleDataPoint dp) {
			return (dp.getBuyOpen() + dp.getBuyClose() + dp.getSellClose()
					+ dp.getSellClose() + dp.getBuyHigh() + dp.getBuyLow()
					+ dp.getSellHigh() + dp.getSellLow()) / 8.0;
		};
	});

	private Evaluator evaluator;

	private CandleValueModel(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public double evaluate(CandleDataPoint dp) {
		return evaluator.evaluate(dp);
	}

	interface Evaluator {
		double evaluate(CandleDataPoint dp);
	}
}
