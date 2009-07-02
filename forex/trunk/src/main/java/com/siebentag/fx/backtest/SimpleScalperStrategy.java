package com.siebentag.fx.backtest;

import com.siebentag.fx.TickDataPoint;

public class SimpleScalperStrategy extends AbstractStrategy
{
	// instance variables
	boolean override = false;
	private Order currentOrder;

	// parameters
	private double difference = 1.5 / 10000.0;
	private int sma1Size = 300;
	private int sma2Size = 900;
	private int sma3Size = 3600;
	private double stopLoss = 20;

	// indicators
	private String i1 = null;
	private String i2 = null;
	private String i3 = null;
	
	
	public SimpleScalperStrategy(BalanceSheet balanceSheet)
	{
		super(balanceSheet);
	}
	
	public String getName()
	{
		return String.format("scalper-%.1f-%1d-%1d-%1d-%.1f", difference*10000.0, sma1Size, sma2Size, sma3Size, stopLoss);
	}
	
	public String toString()
	{
		return String.format("%.1f\t%1d\t%1d\t%1d", difference*10000.0, sma1Size, sma2Size, sma3Size);
	}
	
	public void registerIndicators()
	{
		i1 = IndicatorFactory.createSimpleMovingAverage(sma1Size).getName();
		i2 = IndicatorFactory.createSimpleMovingAverage(sma2Size).getName();
		i3 = IndicatorFactory.createSimpleMovingAverage(sma3Size).getName();
	}
	
	public void tick(TickDataPoint tick)
	{
		double s1 = IndicatorValueCache.getValue(i1, tick.getDate());
		double s2 = IndicatorValueCache.getValue(i2, tick.getDate());
		double s3 = IndicatorValueCache.getValue(i3, tick.getDate());
		
		// check ordering
		// leave position open while SMAs are correctly ordered
		if(!override && s1 > s2+difference && s2 > s3+difference)
		{
			// open long position if not open already
			if(currentOrder == null)
			{
				currentOrder = place(tick, OrderSide.Sell, getLotSize());
			}
		}
		else if(!override && s1 < s2-difference && s2 < s3-difference)
		{
			// open short position if not open already
			if(currentOrder == null)
			{
				currentOrder = place(tick, OrderSide.Buy, getLotSize());
			}
		}
		else 
		{
			override = false;
			
			// close position if one is open
			if(currentOrder != null)
			{
				close(currentOrder, tick);
				currentOrder = null;
			}
		}

		// check if the stop-loss has been breached
		if(currentOrder != null && (currentOrder.getPipProfitLoss(tick) < -stopLoss))
		{
			close(currentOrder, tick);
			currentOrder = null;
			
			override = true;
		}
	}
	
	public void setStopLoss(double sl)
	{
		this.stopLoss = sl;
	}
	
	public void setSMASizes(int s1, int s2, int s3)
	{
		sma1Size = s1;
		sma2Size = s2;
		sma3Size = s3;
	}

	public void setDifference(double diff)
	{
		this.difference = diff / 10000.0;
	}
	
	public Order getCurrentOrder()
	{
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder)
	{
		this.currentOrder = currentOrder;
	}

	public String getSummaryFile()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
