package com.jeff.fx.backtest.strategy.optimiser;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.jeff.fx.backtest.engine.OrderBookReport;

@SuppressWarnings("serial")
public class OptimiserReportTableModel extends DefaultTableModel {

	private double winPercentageThreshold = 50;
	private double priceThreshold = 0;
	private List<OptimiserReportRow> rows;

	public OptimiserReportTableModel() {
		reset();
	}
	
	public OptimiserReportRow getRow(int idx) {
		return rows.get(idx);
	}
	
	public boolean accepts(OrderBookReport report) {

		boolean accept = true;
		
		if(priceThreshold > 0 && report.getBalance() < priceThreshold) 
			accept = false;
		
		if(winPercentageThreshold > 0 && report.getWinPercentage() < winPercentageThreshold)
			accept = false;

		return accept;
	}
	
	public void reset() {
		if(rows != null) {
			rows.clear();
		} else {
			rows = new ArrayList<OptimiserReportRow>();
		}
		
		fireTableDataChanged();
	}
	
	public void addRow(OptimiserReportRow row) {
		
		if(accepts(row.getBookReport())) {
			rows.add(row);
			fireTableRowsInserted(rows.size()-1, rows.size()-1);
		}
	}

	public int getColumnCount() {
		return 7;
	}

	public String getColumnName(int column) {
		switch(column) {
			case 0: return "ID";
			case 1: return "Params";
			case 2: return "Balance";
			case 3: return "Orders";
			case 4: return "Win%";
			case 5: return "Loss%";
			case 6: return "Fitness";
			default: return "";
		}
	}

	public int getRowCount() {
		if(rows == null)
			return 0;
		
		return rows.size();
	}

	public Class<?> getColumnClass(int column) {
		switch(column) {
			case 0: return Integer.class;
			case 1: return String.class;
			case 2: return Double.class;
			case 3: return Integer.class;
			case 4: return Double.class;
			case 5: return Double.class;
			case 6: return Double.class;
			default: return String.class;
		}
	}
	
	public Object getValueAt(int idx, int column) {
		
		OptimiserReportRow row = rows.get(idx);
		
		switch(column) {
			case 0: return row.getId();
			case 1: return row.getParams();
			case 2: return row.getBookReport().getBalance();
			case 3: return row.getBookReport().getOrderCount();
			case 4: return row.getBookReport().getWinPercentage();
			case 5: return row.getBookReport().getLossPercentage();
			case 6: return row.getBookReport().getFitness();
			default: return "";
	}	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public double getWinPercentageThreshold() {
		return winPercentageThreshold;
	}

	public void setWinPercentageThreshold(double winPercentageThreshold) {
		this.winPercentageThreshold = winPercentageThreshold;
	}

	public double getPriceThreshold() {
		return priceThreshold;
	}

	public void setPriceThreshold(double priceThreshold) {
		this.priceThreshold = priceThreshold;
	}
}

