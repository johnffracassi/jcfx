package com.jeff.fx.backtest.strategy.time;

import javax.swing.table.DefaultTableModel;

import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.engine.OrderBookReport;

@SuppressWarnings("serial")
public class OrderBookReportTableModel extends DefaultTableModel {

	private OrderBookReport report;

	public OrderBookReportTableModel() {
	}

	public void update(OrderBook book) {
		report = new OrderBookReport(book);
	}

	public int getRowCount() {
		if(report == null) return 0;
		return 7;
	}

	public Object getValueAt(int row, int col) {
		int i=0;
		if(row == i++) return col == 0 ? "Orders" : report.getOrderCount();
		if(row == i++) return col == 0 ? "Balance" : String.format("%.0f", report.getBalance());
		if(row == i++) return col == 0 ? "Peak/Drawdown" : String.format("%.0f / %.0f", report.getMaxBalance(), report.getMinBalance());
		if(row == i++) return col == 0 ? "Wins" : String.format("%d (%.1f%%)", report.getWins(), report.getWinPercentage()); 
		if(row == i++) return col == 0 ? "Losses" : String.format("%d (%.1f%%)", report.getLosses(), report.getLossPercentage());
		if(row == i++) return col == 0 ? "Best" : String.format("%.0f", report.getHigh());
		if(row == i++) return col == 0 ? "Worst" : String.format("%.0f", report.getLow());
		return "";
	}

	public Class<?> getColumnClass(int column) {
		switch (column) {
			case 0: return String.class;
			case 1: return String.class;
			default: return String.class;
		}
	}
	
	
	public int getColumnCount() {
		return 2;
	}
	
	public String getColumnName(int column) {
		switch (column) {
			case 0: return "Key";
			case 1: return "Value";
			default: return "";
		}
	}
}

