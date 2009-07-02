package com.siebentag.fx.backtest;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalTime;

import com.siebentag.fx.source.Instrument;

public class TimeBasedStrategyFactory
{
	private LocalTime start = new LocalTime(0, 0);
	private LocalTime end = new LocalTime(23, 59);
	private int interval = 20;
	private double lotSize = 0.5;
	private Instrument instrument;

	public List<Strategy> buildStrategies(Instrument instrument)
	{
		this.instrument = instrument;
		
		List<Strategy> list = new LinkedList<Strategy>();

		for (int h1 = start.getHourOfDay(); h1 <= end.getHourOfDay(); h1++)
		{
			for (int m1 = 0; m1 < 60; m1 += interval)
			{
				for (int h2 = start.getHourOfDay(); h2 <= end.getHourOfDay(); h2++)
				{
					for (int m2 = 0; m2 < 60; m2 += interval)
					{
						LocalTime open = new LocalTime(h1, m1);
						LocalTime close = new LocalTime(h2, m2);

						TimeBasedStrategy tbs = new TimeBasedStrategy(new BalanceSheet());
						tbs.setInstrument(instrument);
						tbs.setLotSize(lotSize);
						tbs.setOpenTime(open);
						tbs.setCloseTime(close);
						tbs.setOrderSide(OrderSide.Buy);
						list.add(tbs);
					}
				}
			}
		}

		return list;
	}

	public LocalTime getStart()
	{
		return start;
	}

	public void setStart(LocalTime start)
	{
		this.start = start;
	}

	public LocalTime getEnd()
	{
		return end;
	}

	public void setEnd(LocalTime end)
	{
		this.end = end;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public double getLotSize()
	{
		return lotSize;
	}

	public void setLotSize(double lotSize)
	{
		this.lotSize = lotSize;
	}

	public Instrument getInstrument()
	{
		return instrument;
	}

	public void setInstrument(Instrument instrument)
	{
		this.instrument = instrument;
	}

}
