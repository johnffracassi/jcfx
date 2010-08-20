package com.jeff.fx.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.GenericDialog;

@SuppressWarnings("serial")
@Component
public class AboutAction extends AbstractAction {
	
	public AboutAction() {
		super("About...", new ImageIcon(AboutAction.class.getResource("/images/application_double.png")));
		putValue(SHORT_DESCRIPTION, "About the application");
		putValue(LONG_DESCRIPTION, "About the application");
	}

	public void actionPerformed(ActionEvent ev) {
		GenericDialog gd = new GenericDialog(new AboutPanel(), "FX Workbench");
		gd.setUndecorated(true);
		gd.setSize(350, 190);
		((JPanel)gd.getContentPane()).setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		gd.setVisible(true);
	}
}
