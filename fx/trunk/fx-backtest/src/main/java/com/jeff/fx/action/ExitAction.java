package com.jeff.fx.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class ExitAction extends AbstractAction {
	
	public ExitAction() {
		super("Exit", new ImageIcon(ExitAction.class.getResource("/images/cross.png")));
		putValue(SHORT_DESCRIPTION, "Quit the application");
		putValue(LONG_DESCRIPTION, "Quit the application");
	}

	public void actionPerformed(ActionEvent ev) {
		int option = JOptionPane.showConfirmDialog(null, "Do you really want to quit?");

		if (option == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}
