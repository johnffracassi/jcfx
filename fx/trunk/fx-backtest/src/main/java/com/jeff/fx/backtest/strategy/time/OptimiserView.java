package com.jeff.fx.backtest.strategy.time;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.jeff.fx.backtest.GUIUtil;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class OptimiserView extends JPanel {
	
	private OptimiserParameterTableModel paramModel;
	private JTable tblParams;
	
	public OptimiserView() {
		
		setLayout(new MigLayout("", "[250:400][250:400,grow]", "[200:200:300,top][200:n,grow,top]"));
		
		JPanel pnlParameters = new JPanel();
		add(GUIUtil.frame("Parameters", pnlParameters), "cell 0 0,grow");
		pnlParameters.setLayout(new BorderLayout(0, 0));
		
		paramModel = new OptimiserParameterTableModel();
		tblParams = new JTable(paramModel);
		tblParams.setFillsViewportHeight(true);
		tblParams.setRowSelectionAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tblParams);
		pnlParameters.add(scrollPane);
		
		JPanel pnlStatus = new JPanel();
		add(GUIUtil.frame("Status", pnlStatus), "cell 1 0,grow");
		
		JPanel pnlResults = new JPanel();
		add(GUIUtil.frame("Results", pnlResults), "cell 0 1 2 1,grow");
	}

}
