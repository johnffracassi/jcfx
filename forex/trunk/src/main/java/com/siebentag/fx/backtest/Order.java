package com.siebentag.fx.backtest;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.Instrument;

public abstract class Order
{
	private double lots;
	private TickDataPoint open;
	private TickDataPoint close;
	private double stop;
	private double limit;
	private OrderCloseType closeType = OrderCloseType.Open;

	public abstract double getLimitPrice();
	public abstract boolean limitReached(TickDataPoint tick);
	public abstract double getStopPrice();
	public abstract boolean stopReached(TickDataPoint tick);
	public abstract double getOpenPrice();
	public abstract double getClosePrice();
	public abstract double getPipProfitLoss(TickDataPoint currentTick);
	public abstract OrderSide getSide();
	
	public boolean isOpen()
	{
		return (open != null && close == null);
	}
	
	public boolean isClosed()
	{
		return (open != null && close != null);
	}
	
	public double getProfitLoss()
	{
		return (lots * 10.0) * getPipProfitLoss();
	}
	
	public double getPipProfitLoss()
	{
		if(isClosed())
		{
			return getPipProfitLoss(close);
		}
		
		return 0.0;
	}
	

	public double getLots()
	{
		return lots;
	}

	public void setLots(double lots)
	{
		this.lots = lots;
	}

	public double getStop()
	{
		return stop;
	}

	public void setStop(double stop)
	{
		this.stop = stop;
	}

	public double getLimit()
	{
		return limit;
	}

	public void setLimit(double limit)
	{
		this.limit = limit;
	}

	public Instrument getInstrument()
    {
    	return Instrument.valueOf(getOpen().getInstrument());
    }

	public TickDataPoint getOpen()
	{
		return open;
	}

	public void setOpen(TickDataPoint open)
	{
		this.open = open;
	}

	public TickDataPoint getClose()
	{
		return close;
	}

	public void setClose(TickDataPoint close, OrderCloseType closeType)
	{
		this.close = close;
		this.closeType = closeType;
	}
	
	public OrderCloseType getCloseType()
	{
		return closeType;
	}
	
	public void setCloseType(OrderCloseType closeType)
	{
		this.closeType = closeType;
	}
}
