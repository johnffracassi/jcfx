package com.siebentag.fx.backtest;

import com.siebentag.fx.TickDataPoint;

public class BuyOrder extends Order
{
	public double getOpenPrice()
	{
		return getOpen().getBuy();
	}
	
	public double getClosePrice()
	{
		if(getCloseType() == OrderCloseType.Close)
		{
			return getClose().getSell();
		}
		else if(getCloseType() == OrderCloseType.StopLoss)
		{
			return getStopPrice();
		}
		else if(getCloseType() == OrderCloseType.Limit)
		{
			return getLimitPrice();
		}
		else
		{
			return Double.NaN;
		}
	}

	public double getPipProfitLoss()
	{
		if(isClosed())
		{
			return (getClosePrice() - getOpenPrice()) * 10000.0;
		}
		else
		{
			return Double.NaN;
		}
	}
	
	public double getPipProfitLoss(TickDataPoint currentTick)
	{
		return (currentTick.getSell() - getOpen().getBuy()) * 10000.0;
	}
	
	public OrderSide getSide()
	{
		return OrderSide.Buy;
	}

	public double getLimitPrice()
	{
		return getOpen().getBuy() + getLimit();
	}

	public double getStopPrice()
	{
		return getOpen().getSell() - getStop();
	}
	
	public boolean limitReached(TickDataPoint tick)
	{
		if(getLimit() < 0.00001) return false;
		
		return (tick.getSell() >= getLimitPrice());
	}

	public boolean stopReached(TickDataPoint tick)
	{
		if(getStop() < 0.00001) return false;
		
		return (tick.getSell() <= getStopPrice());
	}
}
