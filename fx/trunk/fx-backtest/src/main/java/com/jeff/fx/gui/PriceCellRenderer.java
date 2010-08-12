package com.jeff.fx.gui;

import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class PriceCellRenderer extends DefaultTableCellRenderer {

	private int dp = 2;
	private String customPattern = null;
	
	public PriceCellRenderer(int dp) {
		this.dp = dp;
		setHorizontalAlignment(RIGHT);
	}
	
	public PriceCellRenderer(String pattern) {
		this.customPattern = pattern;
	}
	
	protected void setValue(Object value) {
		if(value instanceof Double) {
			double x = (Double)value;
			
			if(x == 0.0) {
				setText("-");
			} else {
				setText(String.format(getPattern(), x));
			}
		}
	}
	
	protected String getPattern() {
		return customPattern == null ? "%." + dp + "f" : customPattern;
	}
}
