package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.filter.candletype.def.*;

public enum CandleType
{
    Bull(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return bull.is(dp); }}),
    StrongBull(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return strongBull.is(dp); }}),
    Bear(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return bear.is(dp); }}),
    StrongBear(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return strongBear.is(dp); }}),
    BlackMarabozu(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return blackMarubozu.is(dp); }}),
    WhiteMarabozu(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return whiteMarubozu.is(dp); }}),
    Doji(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return doji.is(dp);}}),
    DragonFlyDoji(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return dragonFlyDoji.is(dp);}}),
    FourPriceDoji(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return fourPriceDoji.is(dp);}}),
    GravestoneDoji(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return gravestoneDoji.is(dp);}}),
    SpinningTop(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return spinningTop.is(dp);}}),
    InvertedHammer(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return invertedHammer.is(dp);}}),
    Hammer(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return hammer.is(dp);}}),
    HangingMan(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return hangingMan.is(dp);}}),
    ShootingStar(new CandleTypeEvaluator() {public boolean evaluate(CandleDataPoint dp) {return shootingStar.is(dp);}});

    private CandleTypeEvaluator evaluator;
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

	private CandleType(CandleTypeEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public boolean evaluate(CandleDataPoint dp) {
		return evaluator.evaluate(dp);
	}
}

