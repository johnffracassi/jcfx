package com.jeff.fx.lfwd.candletype;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lfwd.candletype.def.*;

public enum CandleType
{
    Bull(new Bull()),
    StrongBull(new StrongBull()),
    Bear(new Bear()),
    StrongBear(new StrongBear()),
    BlackMarabozu(new BlackMarubozu()),
    WhiteMarabozu(new WhiteMarubozu()),
    Doji(new Doji()),
    DragonFlyDoji(new DragonFlyDoji()),
    FourPriceDoji(new FourPriceDoji()),
    GravestoneDoji(new GravestoneDoji()),
    SpinningTop(new SpinningTop()),
    InvertedHammer(new InvertedHammer()),
    Hammer(new Hammer()),
    HangingMan(new HangingMan()),
    ShootingStar(new ShootingStar());

    private CandleTypeDef type;

	CandleType(CandleTypeDef type)
    {
		this.type = type;
	}

    public boolean is(CandleDataPoint candle)
    {
		return type.is(candle);
	}
}

