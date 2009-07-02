package com.siebentag.fx.tbv;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import com.siebentag.fx.backtest.BalanceSheet;

public class DataSummaryPanel extends JXPanel
{
	private JXTable table;
	private DataSummaryTableModel model;
	
	public DataSummaryPanel()
	{
		model = new DataSummaryTableModel();
		table = new JXTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 130));
	}
	
	public void setBalanceSheet(BalanceSheet balanceSheet)
	{
		model.setBalanceSheet(balanceSheet);
	}
}
