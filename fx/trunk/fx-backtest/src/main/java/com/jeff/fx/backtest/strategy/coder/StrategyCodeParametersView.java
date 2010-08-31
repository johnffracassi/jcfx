package com.jeff.fx.backtest.strategy.coder;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StrategyCodeParametersView extends JPanel {

	private JTable table;

	public static void main(String[] args) {
		StrategyCodeParametersView scpv = new StrategyCodeParametersView();
		JFrame frame = new JFrame();
		frame.setContentPane(scpv);
		frame.setSize(350, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public StrategyCodeParametersView() {
		setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewParameter = new JButton("");
		btnNewParameter.setIcon(new ImageIcon(StrategyCodeParametersView.class.getResource("/images/add.png")));
		toolBar.add(btnNewParameter);
		
		JButton btnDeleteParameter = new JButton("");
		btnDeleteParameter.setIcon(new ImageIcon(StrategyCodeParametersView.class.getResource("/images/delete.png")));
		toolBar.add(btnDeleteParameter);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setModel(new StrategyCodeParametersModel());
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(1).setPreferredWidth(220);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

	}

	public JTable getTable() {
		return table;
	}
}
