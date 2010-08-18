package com.jeff.fx.backtest.chart;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

@Component
public class NewCandleChartAction extends AbstractAction {
	
	private static final long serialVersionUID = 7750607693375928071L;

	public NewCandleChartAction() {
		
		putValue(SHORT_DESCRIPTION, "New Chart");
		putValue(LONG_DESCRIPTION, "Create a new strategy set");
		putValue(NAME, "New Chart");
	}

	public void actionPerformed(ActionEvent ev) {
		
		Instrument instrument = Instrument.valueOf(AppCtx.getPersistent("newChart.instrument"));
		FXDataSource dataSource = FXDataSource.valueOf(AppCtx.getPersistent("newChart.dataSource"));
		Period period = Period.valueOf(AppCtx.getPersistent("newChart.period"));
		LocalDate startDate = AppCtx.getPersistentDate("newChart.startDate");
		LocalDate endDate = AppCtx.getPersistentDate("newChart.endDate");
		
		NewCandleChartEvent nce = new NewCandleChartEvent(instrument, dataSource, period, startDate, endDate);
		AppCtx.fireEvent(nce);
	}
}
