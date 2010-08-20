package com.jeff.fx.action;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class AboutPanel extends JPanel {

	private static final long serialVersionUID = -5046279782936186411L;

	public AboutPanel() {
		setLayout(new MigLayout("", "[10][100px][grow,fill]", "[][][][][][][][]"));
		
		JLabel lblApplication = new JLabel("Application:");
		add(lblApplication, "cell 1 1");
		
		JLabel lblFxBacktestWorkbench = new JLabel("FX Backtest Workbench");
		add(lblFxBacktestWorkbench, "cell 2 1");
		
		JLabel lblVersion = new JLabel("Version:");
		add(lblVersion, "cell 1 3");
		
		JLabel label_3 = new JLabel("0.01");
		add(label_3, "cell 2 3");
		
		JLabel lblBuildDate = new JLabel("Build Date:");
		add(lblBuildDate, "cell 1 5");
		
		JLabel lblDevelopment = new JLabel("DEVELOPMENT");
		add(lblDevelopment, "cell 2 5");
		
		JLabel lblCopyright = new JLabel("Copyright:");
		add(lblCopyright, "cell 1 7");
		
		JLabel lblcJeffreyCann = new JLabel("(c)2010 Jeffrey Cann");
		add(lblcJeffreyCann, "cell 2 7");

	}

}
