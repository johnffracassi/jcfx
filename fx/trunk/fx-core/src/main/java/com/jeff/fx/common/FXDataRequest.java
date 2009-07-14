package com.jeff.fx.common;

import org.joda.time.LocalDate;


public class FXDataRequest 
{
	private FXDataSource dataSource;
	private Instrument instrument;
	private LocalDate date;
	private Period period;

	public FXDataRequest()
	{
	}
	
	public FXDataRequest(FXDataSource dataSource, Instrument instrument, LocalDate date, Period period) 
	{
		super();
		
		this.dataSource = dataSource;
		this.instrument = instrument;
		this.date = date;
		this.period = period;
	}

	public FXDataSource getDataSource() 
	{
		return dataSource;
	}

	public void setDataSource(FXDataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	public Instrument getInstrument() 
	{
		return instrument;
	}

	public void setInstrument(Instrument instrument) 
	{
		this.instrument = instrument;
	}

	public Period getPeriod() 
	{
		return period;
	}

	public void setPeriod(Period period) 
	{
		this.period = period;
	}

	@Override
	public String toString() 
	{
		return String.format(
						"[dataSource=%s, instrument=%s, period=%s, date=%s]",
						dataSource, instrument, period, date);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
