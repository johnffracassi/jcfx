package com.jeff.fx.backtest.strategy.optimiser;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jeff.fx.backtest.AppCtx;

public class ExecutorParametersController {

	private ExecutorParametersView view;
	
	public ExecutorParametersController() {
	}

	public void setView(ExecutorParametersView pnlExecutorParameters) {
	
		this.view = pnlExecutorParameters;
		
		view.getTxtBalanceThreshold().setText(AppCtx.getPersistent("optimiser.threshold.balance"));
		view.getTxtBalanceThreshold().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent ev) {
				AppCtx.setPersistent("optimiser.threshold.balance", view.getTxtBalanceThreshold().getText());
			}
			
			public void focusGained(FocusEvent ev) {
			}
		});
		
		int winThreshold = AppCtx.getPersistentInt("optimiser.threshold.winPercentage");
		view.getSpinWinThreshold().setModel(new SpinnerNumberModel(winThreshold, 0, 100, 1));
		view.getSpinWinThreshold().setValue(winThreshold);
		view.getSpinWinThreshold().addChangeListener(new ChangeListener() { 
			public void stateChanged(ChangeEvent ev) {
				AppCtx.setPersistent("optimiser.threshold.winPercentage", view.getSpinWinThreshold().getValue());
			}
		});	
	}
}
