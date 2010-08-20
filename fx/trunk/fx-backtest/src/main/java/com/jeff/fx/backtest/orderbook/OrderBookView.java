package com.jeff.fx.backtest.orderbook;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class OrderBookView extends JPanel {
	
	private static final long serialVersionUID = -6425109271851163104L;
	private JTabbedPane tabbedPane;

	public OrderBookView() {
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
