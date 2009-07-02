package com.siebentag.fx.tbv;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import com.siebentag.fx.backtest.BalanceSheet;
import com.siebentag.fx.mv.DoubleRenderer;

public class OrdersTable extends JXPanel
{
	private JXTable table;
	private OrdersTableModel model;
	
	public OrdersTable()
	{
		model = new OrdersTableModel();
		table = new JXTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 450));
		
		table.getColumn(0).setCellRenderer(new DateTimeRenderer());
		table.getColumn(2).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(3).setCellRenderer(new DateTimeRenderer());
		table.getColumn(4).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(5).setCellRenderer(new DoubleRenderer(1, true));

		table.getColumn(0).setWidth(100);
		table.getColumn(1).setWidth(30);
		table.getColumn(2).setWidth(50);
		table.getColumn(3).setWidth(100);
		table.getColumn(4).setWidth(50);
		table.getColumn(5).setWidth(50);
	}
	
	public void setBalanceSheet(BalanceSheet balanceSheet)
	{
		model.setBalanceSheet(balanceSheet);
	}
}