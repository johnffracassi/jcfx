package com.jeff.fx.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class AboutAction extends AbstractAction {
	
	public AboutAction() {
		putValue(SHORT_DESCRIPTION, "About the application");
		putValue(LONG_DESCRIPTION, "About the application");
		putValue(NAME, "About...");
	}

	public void actionPerformed(ActionEvent ev) {
		JOptionPane.showMessageDialog(null, "FX Backtesting Applicaion v0.01\n(c)2010 Jeffrey Cann");
	}
}
