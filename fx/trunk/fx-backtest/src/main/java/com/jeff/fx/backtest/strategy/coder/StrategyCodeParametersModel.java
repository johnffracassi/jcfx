package com.jeff.fx.backtest.strategy.coder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class StrategyCodeParametersModel extends DefaultTableModel {

	private static final long serialVersionUID = 5617270470262295051L;

	private List<StrategyParam> params;
	
	public StrategyCodeParametersModel() {
		params = new ArrayList<StrategyParam>();
	}
	
	public StrategyParam delete(int idx) {
		StrategyParam param = params.remove(idx);
		fireTableRowsDeleted(idx, idx);
		return param;
	}
	
	public List<StrategyParam> getParams() {
		return params;
	}

	public void setParams(List<StrategyParam> parameters) {
		this.params = parameters;
		fireTableDataChanged();
	}

	public StrategyParam getParam(int idx) {
		return params.get(idx);
	}
	
	public void add(StrategyParam param) {
		params.add(param);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void update(List<StrategyParam> params) {
		this.params = params;
		fireTableDataChanged();
	}
	
	public int getRowCount() {
		if(params == null)
			return 0;
		
		return params.size();
	}

	public int getColumnCount() {
		return 2;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch(column) {
			case 0: return String.class;
			case 1: return Class.class;
			default: return String.class;
		}
	}
	
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Name";
			case 1: return "Type";
			default: return "ERROR";
		}
	}

	public boolean isCellEditable(int row, int column) {
		return true;
	}

	public void setValueAt(Object val, int row, int column) {
		StrategyParam param = params.get(row);
		if(column == 0) {
			param.setName((String)val);
		} else if(column == 1) {
			param.setType((Class<?>)val);
		}
		fireTableCellUpdated(row, column);
	}
	
	public Object getValueAt(int row, int column) {
		StrategyParam param = params.get(row);
		if(column == 0) {
			return param.getName();
		} else if(column == 1) {
			return param.getType().getSimpleName();
		}
		return "ERROR";
	}
}
