package com.siebentag.fx.mv;

import java.util.Date;

public class TickAggregationDelta
{
	private Date date;
	private TickAggregation start;
	private TickAggregation end;

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public TickAggregation getOpen()
	{
		return start;
	}

	public void setOpen(TickAggregation open)
	{
		this.start = open;
	}

	public TickAggregation getClose()
	{
		return end;
	}

	public void setClose(TickAggregation close)
	{
		this.end = close;
	}

	public double getShort()
	{
		return start.getSell().getOpen() - end.getBuy().getOpen();
	}
	
	public double getLong()
	{
		return end.getSell().getOpen() - start.getBuy().getOpen();
	}
	
	public TickAggregationDelta(Date date, TickAggregation open, TickAggregation close)
	{
		super();
		this.date = date;
		this.start = open;
		this.end = close;
	}
}
