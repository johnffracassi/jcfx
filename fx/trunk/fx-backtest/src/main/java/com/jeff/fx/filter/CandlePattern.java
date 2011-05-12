package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;

public enum CandlePattern
{
    Hammer(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isHammer(dp);}}),
    SpinningTop(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isSpinningTop(dp);}}),
    Doji(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isDoji(dp);}}),
    FullBull(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isFullBull(dp);}}),
    FullBear(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isFullBear(dp);}}),
    HangingMan(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isHangingMan(dp);}});

    private CandlePatternEvaluator evaluator;

	private CandlePattern(CandlePatternEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public boolean evaluate(CandleDataPoint dp) {
		return evaluator.evaluate(dp);
	}
}

interface CandlePatternEvaluator
{
	boolean evaluate(CandleDataPoint dp);
}