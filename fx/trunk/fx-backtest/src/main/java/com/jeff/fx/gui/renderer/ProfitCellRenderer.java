package com.jeff.fx.gui.renderer;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class ProfitCellRenderer extends PriceCellRenderer {

	private static final Color POSITIVE = new Color(0, 128, 0);
	private static final Color NEGATIVE = new Color(208, 0, 0);
	
	private double value = 0.0;
	
	public ProfitCellRenderer(int dp) {
		super(dp);
		setHorizontalAlignment(RIGHT);
	}
	
	protected void setValue(Object newValue) {
		
		if(newValue instanceof Double) {
			this.value = (Double)newValue;
		} else if(newValue instanceof Integer) {
			this.value = ((Integer)newValue).doubleValue();
		} else if(newValue instanceof Long) {
			this.value = ((Long)newValue).doubleValue();
		} else if(newValue instanceof Float) {
			this.value = ((Float)newValue).doubleValue();
		}

		super.setValue(this.value);
	}
	
	public void paint(Graphics g) {
		
		if(value > 0.0) {
			setForeground(POSITIVE);
		} else if(value < 0.0) {
			setForeground(NEGATIVE);
		} else {
			setForeground(Color.BLACK);
		}
		
		super.paint(g);
	}
}
