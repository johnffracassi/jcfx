package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;

public enum CandlePattern
{
    Doji(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isDoji(dp);}}),
    SpinningTop(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isSpinningTop(dp);}}),
    Bull(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return dp.getSize() > 5;}}),
    FullBull(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isFullBull(dp);}}),
    Bear(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return dp.getSize() < -5;}}),
    FullBear(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isFullBear(dp);}}),
    Hammer(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isHammer(dp);}}),
    HangingMan(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return CandlePatternType.isShootingStar(dp);}});

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