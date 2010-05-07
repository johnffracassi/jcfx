package com.jeff.fx.backtest;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

class NewChartAction extends AbstractAction {
	
	private static final long serialVersionUID = 7750607693375928071L;

	public void actionPerformed(ActionEvent ev) {
		Instrument instrument = Instrument.valueOf(AppCtx.getString("newChart.instrument"));
		FXDataSource dataSource = FXDataSource.valueOf(AppCtx.getString("newChart.dataSource"));
		Period period = Period.valueOf(AppCtx.getString("newChart.period"));
		LocalDate startDate = AppCtx.getDate("newChart.startDate");
		LocalDate endDate = AppCtx.getDate("newChart.endDate");
		
		NewChartEvent nce = new NewChartEvent(instrument, dataSource, period, startDate, endDate);
		AppCtx.fireEvent(nce);
	}
}
