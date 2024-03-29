package com.jeff.fx.backtest.strategy.time;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.AppCtx;

@Component
public class NewTimeStrategyAction extends AbstractAction {
	
	private static final long serialVersionUID = 7750607693375928071L;

	public NewTimeStrategyAction() {
		putValue(SHORT_DESCRIPTION, "Time Strategy");
		putValue(LONG_DESCRIPTION, "Create a new chart for the Time Strategy");
		putValue(NAME, "Time Strategy");
	}

	public void actionPerformed(ActionEvent ev) {
		AppCtx.fireEvent(new NewTimeStrategyEvent());
	}
}
