package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

@SuppressWarnings("serial")
public class OrderBookView extends JXPanel {

	private OrderBookTableModel tableModel = new OrderBookTableModel();;
	
	public OrderBookView() {
	
		setLayout(new BorderLayout());

		JXTable table = new JXTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void update(OrderBookModel model) {
		tableModel.update(model.getOrderBook());
		tableModel.fireTableDataChanged();
	}	
}
