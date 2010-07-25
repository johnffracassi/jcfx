package com.jeff.fx.backtest.strategy.time;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import com.siebentag.gui.VerticalFlowLayout;

public class TimeStrategyOptimiserView extends JXPanel {

	private JXLabel lblTitle = null;
	
	public TimeStrategyOptimiserView() {
		setLayout(new VerticalFlowLayout());
		lblTitle = new JXLabel("Optimiser");
		add(lblTitle);
	}
}
