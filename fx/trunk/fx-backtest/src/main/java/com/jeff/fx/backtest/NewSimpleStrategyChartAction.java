package com.jeff.fx.backtest;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.datastore.DataManager;

@Component
class NewSimpleStrategyChartAction extends AbstractAction {
	
	private static final long serialVersionUID = 7750607693375928071L;

	public NewSimpleStrategyChartAction() {
		putValue(SHORT_DESCRIPTION, "Create chart");
		putValue(LONG_DESCRIPTION, "Create a new chart for the SimpleStrategy");
		putValue(NAME, "Create Chart");
	}

	public void actionPerformed(ActionEvent ev) {
		AppCtx.fireEvent(new NewSimpleStrategyChartEvent());
	}
}
