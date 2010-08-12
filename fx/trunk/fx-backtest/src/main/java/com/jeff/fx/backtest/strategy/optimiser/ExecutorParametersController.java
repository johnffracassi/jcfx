package com.jeff.fx.backtest.strategy.optimiser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;

import javax.swing.DefaultComboBoxModel;
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
		
		view.getCboThreadCount().setModel(new DefaultComboBoxModel(new Integer[] {
				1, 2, 3, 4, 6, 8, 12, 16, 20, 24, 32, 48, 64, 128
		}));
		view.getCboThreadCount().setSelectedItem(AppCtx.getPersistentInt("multiThreadExecutor.threads"));
		view.getCboThreadCount().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.setPersistent("multiThreadExecutor.threads", view.getCboThreadCount().getSelectedItem());
			}
		});
		
		view.getCboBlockSize().setModel(new DefaultComboBoxModel(new Integer[] {
				1, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000
		}));
		view.getCboBlockSize().setSelectedItem(AppCtx.getPersistentInt("multiThreadExecutor.blockSize"));
		view.getCboBlockSize().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.setPersistent("multiThreadExecutor.blockSize", view.getCboBlockSize().getSelectedItem());
			}
		});
		
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
