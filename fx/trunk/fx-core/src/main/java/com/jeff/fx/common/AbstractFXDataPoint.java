package com.jeff.fx.common;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public abstract class AbstractFXDataPoint implements FXDataPoint, Comparable<AbstractFXDataPoint>, Serializable
{
	private static final long serialVersionUID = 1963158717204537030L;

	private Instrument instrument;
	private FXDataSource dataSource;
	private LocalDateTime date;
	private long buyVolume;
	private long sellVolume;

	public AbstractFXDataPoint()
	{
	}

	public AbstractFXDataPoint(AbstractFXDataPoint dataPoint)
	{
		this.instrument = dataPoint.instrument;
		this.dataSource = dataPoint.dataSource;
		this.date = new LocalDateTime(dataPoint.date);
		this.buyVolume = dataPoint.buyVolume;
		this.sellVolume = dataPoint.sellVolume;
	}
	
	public int compareTo(AbstractFXDataPoint o)
    {
		int instrumentCompare = instrument.compareTo(o.instrument);
		if(instrumentCompare != 0) return instrumentCompare;
		
		int dateCompare = date.compareTo(o.date);
		return dateCompare;
    }

	public long getBuyVolume()
    {
    	return buyVolume;
    }

	public void setBuyVolume(long buyVolume)
    {
    	this.buyVolume = buyVolume;
    }

	public long getSellVolume()
    {
    	return sellVolume;
    }

	public void setSellVolume(long sellVolume)
    {
    	this.sellVolume = sellVolume;
    }

	public Instrument getInstrument()
	{
		return instrument;
	}

	public void setInstrument(Instrument instrument)
	{
		this.instrument = instrument;
	}

	public FXDataSource getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(FXDataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public LocalDateTime getDate()
	{
		return date;
	}
	
	public void setDateTime(LocalDateTime date)
	{
		this.date = date;
	}
}
