package com.siebentag.fx.source;

import java.util.Date;

import com.siebentag.util.DateUtil;


public class DataDownloadJob
{
	private int jobId;
	private String dataSource;
	private Date startDate;
	private Instrument instrument;
	private String status;

	public String toString()
	{
		return instrument + " - " + DateUtil.format(startDate) + " (" + DateUtil.formatHour(startDate) + ")";
	}
	
	public int getJobId()
	{
		return jobId;
	}

	public void setJobId(int jobId)
	{
		this.jobId = jobId;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Instrument getInstrument()
	{
		return instrument;
	}

	public void setInstrument(Instrument instrument)
	{
		this.instrument = instrument;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDataSource()
    {
    	return dataSource;
    }

	public void setDataSource(String dataSource)
    {
    	this.dataSource = dataSource;
    }
}
