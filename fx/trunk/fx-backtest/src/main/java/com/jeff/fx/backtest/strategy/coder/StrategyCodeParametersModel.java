package com.jeff.fx.backtest.strategy.coder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDateTime;

public class StrategyCodeParametersModel extends DefaultTableModel {

	private List<StrategyParam> params;
	
	public StrategyCodeParametersModel() {
		params = new ArrayList<StrategyParam>();
		params.add(new StrategyParam("openTime", LocalDateTime.class));
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
			case 1: return String.class;
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
			try {
				param.setType(Class.forName((String)val));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Object getValueAt(int row, int column) {
		StrategyParam param = params.get(row);
		if(column == 0) {
			return param.getName();
		} else if(column == 1) {
			return param.getType().getName();
		}
		return "ERROR";
	}
}
