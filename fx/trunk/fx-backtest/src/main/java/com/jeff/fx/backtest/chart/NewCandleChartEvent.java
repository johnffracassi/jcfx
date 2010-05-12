package com.jeff.fx.backtest.chart;

import org.joda.time.LocalDate;

import com.jeff.fx.backtest.FXActionEvent;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class NewCandleChartEvent implements FXActionEvent {
	
	private Instrument instrument;
	private FXDataSource dataSource;
	private Period period;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public NewCandleChartEvent(Instrument instrument, FXDataSource dataSource, Period period, LocalDate startDate, LocalDate endDate) {
		
		super();
		
		this.instrument = instrument;
		this.dataSource = dataSource;
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public FXDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(FXDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
