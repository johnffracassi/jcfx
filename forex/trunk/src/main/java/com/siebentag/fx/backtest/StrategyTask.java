package com.siebentag.fx.backtest;

import java.util.List;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.Instrument;

public class StrategyTask implements Runnable
{
	private Strategy strategy;
	private List<TickDataPoint> ticks;
	
	public StrategyTask(Strategy s, List<TickDataPoint> ticks)
	{
		this.strategy = s;
		this.ticks = ticks;
	}

	public void run()
	{
		try
		{
			for(TickDataPoint tick : ticks)
			{
				if(strategy.acceptsInstrument(Instrument.valueOf(tick.getInstrument())))
				{
					strategy.tick(tick);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
