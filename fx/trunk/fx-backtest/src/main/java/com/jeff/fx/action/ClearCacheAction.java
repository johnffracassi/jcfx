package com.jeff.fx.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jeff.fx.backtest.AppCtx;

@SuppressWarnings("serial")
public class ClearCacheAction extends AbstractAction {
	
	public ClearCacheAction() {
		putValue(SHORT_DESCRIPTION, "Clear Cache");
		putValue(LONG_DESCRIPTION, "Clear the data store cache");
		putValue(NAME, "Clear Cache");
	}

	public void actionPerformed(ActionEvent ev) {
		AppCtx.getDataManager().clearStoreCache();
	}
}
