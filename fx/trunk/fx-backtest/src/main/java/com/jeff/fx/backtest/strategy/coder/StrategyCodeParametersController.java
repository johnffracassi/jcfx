package com.jeff.fx.backtest.strategy.coder;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.TimeOfWeek;

public class StrategyCodeParametersController {

	StrategyCodeParametersView view;

	public StrategyCodeParametersController() {
		
		view = new StrategyCodeParametersView();
		
		view.getTable().getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(
				new Class<?>[] { 
						String.class,
						Integer.class,
						Double.class,
						Float.class,
						LocalDate.class,
						LocalTime.class,
						LocalDateTime.class,
						TimeOfWeek.class,
						CandleValueModel.class,
						Instrument.class,
						FXDataSource.class
		}))));
		
	}

	public StrategyCodeParametersView getView() {
		return view;
	}
}
