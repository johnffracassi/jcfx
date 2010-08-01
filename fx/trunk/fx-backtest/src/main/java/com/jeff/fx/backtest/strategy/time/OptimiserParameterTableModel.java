package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class OptimiserParameterTableModel extends DefaultTableModel {

	private List<OptimiserParameter> params = new ArrayList<OptimiserParameter>();
	
	public OptimiserParameterTableModel() {
		addParameter(new OPInteger("stopLoss", 0, 100, 20));
		addParameter(new OPInteger("takeProfit", 0, 100, 20));
		addParameter(new OPInteger("open", 1380, 1560, 15));
		addParameter(new OPInteger("close", 1620, 1860, 15));
	}
	
	public void addParameter(OptimiserParameter param) {
		params.add(param);
	}
	
	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int arg0) {
		switch(arg0) {
			case 0: return "Key";
			case 1: return "Start";
			case 2: return "End";
			case 3: return "Step";
			case 4: return "Steps";
			default: return "XXX";
		}
	}

	public int getRowCount() {
		if(params == null) {
			return 0;
		}
		
		return params.size() + 1;
	}

	public Object getValueAt(int row, int col) {
		
		if(row == params.size()) {
			if(col == 4) {
				int perms = 1;
				for(OptimiserParameter param : params) {
					perms *= param.getStepCount();
				}
				return perms;
			} else if(col == 0) {
				return "  Permutations";
			} else {
				return "";
			}
		}
		
		switch(col) {
			case 0: return params.get(row).getKey();
			case 1: return params.get(row).getStart();
			case 2: return params.get(row).getEnd();
			case 3: return params.get(row).getStep();
			case 4: return params.get(row).getStepCount();
			default: return "XXX";
	}	}

	public boolean isCellEditable(int row, int col) {
		return (col != 0 && col != 4 && row != params.size());
	}
	
	public void setValueAt(Object val, int row, int col) {
		
		if(col == 0 || col == 4 || row == params.size()) {
			return;
		}
		
		OptimiserParameter param = params.get(row);
		
		if(col == 1) {
			param.setStart(param.fromString(String.valueOf(val)));
		} else if (col == 2) {
			param.setEnd(param.fromString(String.valueOf(val)));
		} else if (col == 3) {
			param.setStep(param.fromString(String.valueOf(val)));
		}
		
		fireTableCellUpdated(row, 4);
		fireTableCellUpdated(params.size(), 4);
	}

	public List<OptimiserParameter> getParameters() {
		return params;
	}

	public void setParams(List<OptimiserParameter> params) {
		this.params = params;
	}
}
