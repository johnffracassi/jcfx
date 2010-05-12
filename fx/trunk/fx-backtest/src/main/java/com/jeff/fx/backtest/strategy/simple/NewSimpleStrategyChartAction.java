package com.jeff.fx.backtest.strategy.simple;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.AppCtx;

@Component
public class NewSimpleStrategyChartAction extends AbstractAction {
	
	private static final long serialVersionUID = 7750607693375928071L;

	public NewSimpleStrategyChartAction() {
		putValue(SHORT_DESCRIPTION, "Simple Strategy");
		putValue(LONG_DESCRIPTION, "Create a new chart for the Simple Strategy");
		putValue(NAME, "Simple Strategy");
	}

	public void actionPerformed(ActionEvent ev) {
		AppCtx.fireEvent(new NewSimpleStrategyChartEvent());
	}
}
