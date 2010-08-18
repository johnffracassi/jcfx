package com.jeff.fx.backtest;

import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;

public class AppPrefsController {
	
	private AppPrefsPanel view;
	
	public AppPrefsController() {
		view = new AppPrefsPanel();
//		view.getCboLookAndFeel().setModel(new DefaultComboBoxModel(UIManager.getInstalledLookAndFeels()));
	}
	
	public AppPrefsPanel getView() {
		return view;
	}
}
