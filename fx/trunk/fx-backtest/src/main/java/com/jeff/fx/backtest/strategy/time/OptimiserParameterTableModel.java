package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class OptimiserParameterTableModel extends DefaultTableModel {

	private List<OptimiserParameter> params = new ArrayList<OptimiserParameter>();
	
	public OptimiserParameterTableModel() {
		addParameter(new OPInteger("stopLoss", 0, 200, 10));
		addParameter(new OPInteger("takeProfit", 0, 200, 10));
		addParameter(new OPInteger("open", 0, 10000, 15));
		addParameter(new OPInteger("close", 0, 10000, 15));
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
}

class OPInteger extends OptimiserParameter<Integer,Integer> {

	public OPInteger(String arg0, int arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public Integer fromString(String val) {
		return new Integer(val);
	}

	public int getStepCount() {
		return (getEnd() - getStart()) / getStep();
	}
}

class OPDouble extends OptimiserParameter<Double,Double> {

	public OPDouble(String arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public Double fromString(String val) {
		return new Double(val);
	}

	public int getStepCount() {
		return (int)((getEnd() - getStart()) / getStep());
	}
}

abstract class OptimiserParameter<T,S> {
	
	private String key;
	private T start;
	private T end;
	private S step;
	
	public OptimiserParameter(String key, T start, T end, S step) {
		super();
		this.key = key;
		this.start = start;
		this.end = end;
		this.step = step;
	}

	public abstract T fromString(String val);
	public abstract int getStepCount();
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getStart() {
		return start;
	}

	public void setStart(T start) {
		this.start = start;
	}

	public T getEnd() {
		return end;
	}

	public void setEnd(T end) {
		this.end = end;
	}

	public S getStep() {
		return step;
	}

	public void setStep(S step) {
		this.step = step;
	}
}

