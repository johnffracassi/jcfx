package com.jeff.fx.gui;

import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class PriceCellRenderer extends DefaultTableCellRenderer {

	private int dp = 2;
	
	public PriceCellRenderer(int dp) {
		this.dp = dp;
		setHorizontalAlignment(RIGHT);
	}
	
	protected void setValue(Object value) {
		if(value instanceof Double) {
			double x = (Double)value;
			
			if(x == 0.0) {
				setText("-");
			} else {
				setText(String.format("%." + dp + "f", x));
			}
		}
	}
}
