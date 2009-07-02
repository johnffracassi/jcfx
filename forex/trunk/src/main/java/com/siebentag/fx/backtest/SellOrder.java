package com.siebentag.fx.backtest;

import com.siebentag.fx.TickDataPoint;

public class SellOrder extends Order
{
	public double getOpenPrice()
	{
		return getOpen().getSell();
	}
	
	public double getClosePrice()
	{
		if(getCloseType() == OrderCloseType.Close)
		{
			return getClose().getBuy();
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
			return (getOpenPrice() - getClosePrice()) * 10000.0;
		}
		else
		{
			return Double.NaN;
		}
	}
	
	public double getPipProfitLoss(TickDataPoint currentTick)
	{
		return (getOpenPrice() - currentTick.getBuy()) * 10000.0;
	}
	
	public OrderSide getSide()
	{
		return OrderSide.Sell;
	}

	public double getLimitPrice()
	{
		return getOpen().getSell() - getLimit();
	}

	public double getStopPrice()
	{
		return getOpen().getBuy() + getStop();
	}
	
	public boolean limitReached(TickDataPoint tick)
	{
		if(getLimit() < 0.00001) return false;
		
		return (tick.getBuy() <= getLimitPrice());
	}

	public boolean stopReached(TickDataPoint tick)
	{
		if(getStop() < 0.00001) return false;
		
		return (tick.getBuy() >= getStopPrice());
	}
}
