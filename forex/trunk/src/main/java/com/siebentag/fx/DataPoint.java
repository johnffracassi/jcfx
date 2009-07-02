package com.siebentag.fx;

import java.util.Date;

import org.joda.time.LocalDateTime;

import com.siebentag.fx.source.FXDataSource;

public abstract class DataPoint implements FXDataPoint, Comparable<DataPoint>
{
	private int jobId;
	private String instrument;
	private FXDataSource dataSource;
	private Date date;
	private long buyVolume;
	private long sellVolume;

	public DataPoint()
	{
	}

	public DataPoint(DataPoint dataPoint)
	{
		this.jobId = dataPoint.jobId;
		this.instrument = dataPoint.instrument;
		this.dataSource = dataPoint.dataSource;
		this.date = (dataPoint.date == null ? null : (Date)dataPoint.date.clone());
		this.buyVolume = dataPoint.buyVolume;
		this.sellVolume = dataPoint.sellVolume;
	}
	
	public int compareTo(DataPoint o)
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

	public String getInstrument()
	{
		return instrument;
	}

	public void setInstrument(String instrument)
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

	public int getJobId()
	{
		return jobId;
	}

	public void setJobId(int jobId)
	{
		this.jobId = jobId;
	}

	public LocalDateTime getLocalDateTime()
	{
		return new LocalDateTime(getDate().getTime());
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
}
