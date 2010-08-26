package com.jeff.fx.backtest.strategy.coder;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.AppCtx;

@Component
public class NewStrategyCoderAction extends AbstractAction {
	
	private static final long serialVersionUID = 7750607693375928071L;

	public NewStrategyCoderAction() {
		putValue(SHORT_DESCRIPTION, "Strategy Coder");
		putValue(LONG_DESCRIPTION, "Create a strategy coder window");
		putValue(NAME, "Strategy Coder");
	}

	public void actionPerformed(ActionEvent ev) {
		AppCtx.fireEvent(new NewStrategyCoderEvent());
	}
}
