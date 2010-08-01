package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class OptimiserReportTableModel extends DefaultTableModel {
	
	private List<OptimiserReportRow> rows;

	public OptimiserReportTableModel() {
		reset();
	}
	
	public OptimiserReportRow getRow(int idx) {
		return rows.get(idx);
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
		rows.add(row);
		fireTableRowsInserted(rows.size()-1, rows.size()-1);
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Order";
			case 1: return "Params";
			case 2: return "Balance";
			case 3: return "Wins";
			case 4: return "Losses";
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
			case 3: return Double.class;
			case 4: return Double.class;
			default: return String.class;
		}
	}
	
	public Object getValueAt(int idx, int column) {
		
		OptimiserReportRow row = rows.get(idx);
		
		switch(column) {
			case 0: return row.getId();
			case 1: return row.getParams();
			case 2: return row.getBookReport().getBalance();
			case 3: return row.getBookReport().getWinPercentage();
			case 4: return row.getBookReport().getLossPercentage();
			default: return "";
	}	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}

