package com.siebentag.fx;

public class TickDataPoint extends DataPoint
{
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
		return getInstrument() + " - " + getDate() + " - " + buy + "/" + sell + " (" + getBuyVolume() + "/" + getSellVolume() + ")";
	}
}
