package com.siebentag.fx.backtest;

import java.io.File;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.Instrument;

public interface Strategy
{
	boolean acceptsInstrument(Instrument instrument);
	void tick(TickDataPoint tick);
	String getName();
	String getSummaryFile();
	BalanceSheet getBalanceSheet();
	void registerIndicators();
	
}
