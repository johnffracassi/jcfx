package com.jeff.fx.backtest.strategy;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.gui.SliderLine;
import com.siebentag.gui.VerticalFlowLayout;

public class CommonStrategyPanel extends JXPanel {
	
	private static final long serialVersionUID = 7675491170716141950L;

	public CommonStrategyPanel(final StrategyPropertyChangeListener spcl) {
		
		SliderLine pnlTakeProfit = new SliderLine("common.takeProfit", "Take Profit", 0, 100);
		SliderLine pnlStopLoss = new SliderLine("common.stopLoss", "Stop Loss", 0, 100);
		setLayout(new VerticalFlowLayout(0));
		add(pnlStopLoss);
		add(pnlTakeProfit);
		
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				try {
					spcl.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		
		pnlTakeProfit.addChangeListener(listener);
		pnlStopLoss.addChangeListener(listener);
	}
}

