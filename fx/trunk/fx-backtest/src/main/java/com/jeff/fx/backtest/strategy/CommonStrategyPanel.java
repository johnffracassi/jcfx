package com.jeff.fx.backtest.strategy;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.gui.SliderLine;
import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class CommonStrategyPanel extends JXPanel {
	
	private int stopLoss = 0;
	private int takeProfit = 0;
	
	public CommonStrategyPanel(final StrategyPropertyChangeListener spcl) {
		
		final SliderLine pnlTakeProfit = new SliderLine("common.takeProfit", "Take Profit", 0, 100);
		final SliderLine pnlStopLoss = new SliderLine("common.stopLoss", "Stop Loss", 0, 100);
		setLayout(new VerticalFlowLayout(0));
		add(pnlStopLoss);
		add(pnlTakeProfit);
		
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				try {
					stopLoss = pnlStopLoss.getValue();
					takeProfit = pnlTakeProfit.getValue();
					spcl.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		
		pnlTakeProfit.addChangeListener(listener);
		pnlStopLoss.addChangeListener(listener);
	}
	
	public int getStopLoss() {
		return stopLoss;
	}
	
	public int getTakeProfit() {
		return takeProfit;
	}
}

