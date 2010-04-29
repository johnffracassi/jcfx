package com.jeff.fx.common;

import org.joda.time.LocalDate;

public class FXDataRequest {
	
	private FXDataSource dataSource;
	private Instrument instrument;
	private LocalDate date;
	private LocalDate endDate;
	private Period period;

	public FXDataRequest() {
	}

	public FXDataRequest(FXDataRequest request) {
		this(request.getDataSource(), request.getInstrument(), request.getDate(), request.getEndDate(), request.getPeriod());
	}
	
	public FXDataRequest(FXDataSource dataSource, Instrument instrument, LocalDate date, Period period) {
		
		super();
		this.dataSource = dataSource;
		this.instrument = instrument;
		this.period = period;
		this.date = date;
	}

	public FXDataRequest(FXDataSource dataSource, Instrument instrument, LocalDate startDate, LocalDate endDate, Period period) {
		
		this(dataSource, instrument, startDate, period);
		this.endDate = endDate;
	}

	public FXDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(FXDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return String.format(
				"[dataSource=%s, instrument=%s, period=%s, date=%s]",
				dataSource, instrument, period, date);
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public boolean isRangeOfDates() {
		return endDate != null;
	}
}
