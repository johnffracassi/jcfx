package com.jeff.fx.backtest.strategy.coder;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import java.awt.Component;

public class StrategyCodeParametersView extends JPanel {

	private static final long serialVersionUID = -2350916702309931823L;
	private JTable table;
	private JButton btnNewParameter;
	private JButton btnDeleteParameter;

	public StrategyCodeParametersView() {
		setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(toolBar, BorderLayout.NORTH);
		
		btnNewParameter = new JButton("");
		btnNewParameter.setToolTipText("Create a new parameter");
		btnNewParameter.setIcon(new ImageIcon(StrategyCodeParametersView.class.getResource("/images/add.png")));
		toolBar.add(btnNewParameter);
		
		btnDeleteParameter = new JButton("");
		btnDeleteParameter.setToolTipText("Remove the selected parameter");
		btnDeleteParameter.setIcon(new ImageIcon(StrategyCodeParametersView.class.getResource("/images/delete.png")));
		toolBar.add(btnDeleteParameter);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

	}

	public JTable getTable() {
		return table;
	}
	public JButton getBtnNew() {
		return btnNewParameter;
	}
	public JButton getBtnDelete() {
		return btnDeleteParameter;
	}
}
