package com.siebentag.fx.backtest;

import java.util.Date;

import com.siebentag.fx.TickDataPoint;

public interface Indicator
{
	void tick(TickDataPoint tick);
	double getValue(Date date);
}