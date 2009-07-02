package com.siebentag.fx.source.dukascopy;

public enum DukascopyInstrument
{
	AUDJPY(60),
	AUDUSD(10),
	CADJPY(767),
	CHFJPY(521),
	EURCHF(511),
	EURGBP(510),
	EURJPY(509),
	EURSAR(20),
	EURSEK(29),
	EURUSD(1),
	GBPCHF(518),
	GBPEUR(516),
	GBPJOD(532),
	GBPJPY(517),
	GBPSAR(543),
	GBPUSD(2),
	JPYCHF(515), 
	NZDUSD(11),
	Palladium(336),
	Platinum(335),
	USDCAD(9),
	USDCHF(3),
	USDDKK(13),
	USDEGP(5),
	USDJOD(6),
	USDJPY(4),
	USDNOK(12),
	USDQAR(7),
	USDSAR(15),
	USDSEK(14),
	USDSGD(30),
	USDTND(16),
	USDZAR(74),
	Gold(334),
	Silver(333);
	
	private int code;
	
	DukascopyInstrument(int code)
	{
		this.code = code;
	}
	
	public String getCode()
	{
		return String.valueOf(code);
	}
}
