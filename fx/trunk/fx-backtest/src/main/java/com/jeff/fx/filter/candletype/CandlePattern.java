package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

public enum CandlePattern
{
    Bull(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return bull.is(dp); }}),
    StrongBull(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return strongBull.is(dp); }}),
    Bear(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return bear.is(dp); }}),
    StrongBear(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return strongBear.is(dp); }}),
    BlackMarabozu(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return whiteMarubozu.is(dp); }}),
    WhiteMarabozu(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return blackMarubozu.is(dp); }}),
    Doji(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return doji.is(dp);}}),
    DragonFlyDoji(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return dragonFlyDoji.is(dp);}}),
    FourPriceDoji(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return fourPriceDoji.is(dp);}}),
    GravestoneDoji(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return gravestoneDoji.is(dp);}}),
    SpinningTop(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return spinningTop.is(dp);}}),
    InvertedHammer(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return invertedHammer.is(dp);}}),
    Hammer(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return hammer.is(dp);}}),
    HangingMan(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return hangingMan.is(dp);}}),
    ShootingStar(new CandlePatternEvaluator() {public boolean evaluate(CandleDataPoint dp) {return shootingStar.is(dp);}});

    private CandlePatternEvaluator evaluator;
    private static Bull bull = new Bull();
    private static Bear bear = new Bear();
    private static StrongBull strongBull = new StrongBull();
    private static StrongBear strongBear = new StrongBear();
    private static Doji doji = new Doji();
    private static DragonFlyDoji dragonFlyDoji = new DragonFlyDoji();
    private static FourPriceDoji fourPriceDoji = new FourPriceDoji();
    private static GravestoneDoji gravestoneDoji = new GravestoneDoji();
    private static WhiteMarubozu whiteMarubozu = new WhiteMarubozu();
    private static BlackMarubozu blackMarubozu = new BlackMarubozu();
    private static SpinningTop spinningTop = new SpinningTop();
    private static Hammer hammer = new Hammer();
    private static InvertedHammer invertedHammer = new InvertedHammer();
    private static HangingMan hangingMan = new HangingMan();
    private static ShootingStar shootingStar = new ShootingStar();

	private CandlePattern(CandlePatternEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public boolean evaluate(CandleDataPoint dp) {
		return evaluator.evaluate(dp);
	}
}

