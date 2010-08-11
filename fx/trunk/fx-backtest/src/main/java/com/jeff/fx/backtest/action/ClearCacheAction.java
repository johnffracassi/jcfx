package com.jeff.fx.backtest.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.BackTestDataManager;

@SuppressWarnings("serial")
@Component
public class ClearCacheAction extends AbstractAction {
	
	@Autowired
	private BackTestDataManager dataManager;
	
	public ClearCacheAction() {
		putValue(SHORT_DESCRIPTION, "Clear Cache");
		putValue(LONG_DESCRIPTION, "Clear the data store cache");
		putValue(NAME, "Clear Cache");
	}

	public void actionPerformed(ActionEvent ev) {
		dataManager.clearStoreCache();
	}
}
