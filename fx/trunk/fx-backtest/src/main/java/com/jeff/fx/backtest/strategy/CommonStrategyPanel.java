package com.jeff.fx.backtest.strategy;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.gui.SliderLine;
import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class CommonStrategyPanel extends JXPanel {
	
	final SliderLine pnlTakeProfit;
	final SliderLine pnlStopLoss;
	
	public CommonStrategyPanel(final StrategyPropertyChangeListener spcl) {
		
		pnlTakeProfit = new SliderLine("common.takeProfit", "Take Profit", 0, 250, 5);
		pnlStopLoss = new SliderLine("common.stopLoss", "Stop Loss", 0, 250, 5);
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
	
	public void setStopLoss(int sl) {
		pnlStopLoss.setValue(sl);
	}
	
	public void setTakeProfit(int tp) {
		pnlTakeProfit.setValue(tp);
	}
	
	public int getStopLoss() {
		return pnlStopLoss.getValue();
	}
	
	public int getTakeProfit() {
		return pnlTakeProfit.getValue();
	}
}

