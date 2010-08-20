package com.jeff.fx.backtest.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.AppPrefsController;

@Component
public class ApplicationPreferencesAction extends AbstractAction {

	private static final long serialVersionUID = -4770276444075523321L;
	private static Logger log = Logger.getLogger(ApplicationPreferencesAction.class);

	@Autowired
	private AppPrefsController controller;
	
	public ApplicationPreferencesAction() {
		putValue(SHORT_DESCRIPTION, "Application Preferences");
		putValue(LONG_DESCRIPTION, "Show the Application Preferences dialog");
		putValue(NAME, "Application Preferences");
	}

	public void actionPerformed(ActionEvent ev) {
		log.debug("application preferences action triggered");
		controller.showDialog();
	}
}
