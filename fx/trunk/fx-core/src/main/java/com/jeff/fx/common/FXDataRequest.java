package com.jeff.fx.common;

import org.joda.time.Interval;

public class FXDataRequest {
	private FXDataSource dataSource;
	private Instrument instrument;
	private Interval interval;
	private Period period;

	public FXDataRequest() {
	}

	public FXDataRequest(FXDataSource dataSource, Instrument instrument,
			Interval interval, Period period) {
		super();

		this.dataSource = dataSource;
		this.instrument = instrument;
		this.period = period;
		this.interval = interval;
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

	@Override
	public String toString() {
		return String.format(
				"[dataSource=%s, instrument=%s, period=%s, interval=%s]",
				dataSource, instrument, period, interval);
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}
}
