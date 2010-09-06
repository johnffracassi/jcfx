package com.jeff.fx.common;

import java.io.Serializable;

public class TickDataPoint extends AbstractFXDataPoint implements Serializable
{
	private static final long serialVersionUID = -1314194431212144949L;

	private double buy;
	private double sell;

	public double getBuy()
	{
		return buy;
	}

	public void setBuy(double buy)
	{
		this.buy = buy;
	}

	public double getSell()
	{
		return sell;
	}

	public void setSell(double sell)
	{
		this.sell = sell;
	}

	public double getAveragePrice()
	{
		return (buy + sell) / 2.0;
	}
	
	public String toString()
	{
		return getInstrument() + " - " + getDateTime() + " - " + buy + "/" + sell + " (" + getBuyVolume() + "/" + getSellVolume() + ")";
	}

	public Period getPeriod() 
	{
		return Period.Tick;
	}
}
