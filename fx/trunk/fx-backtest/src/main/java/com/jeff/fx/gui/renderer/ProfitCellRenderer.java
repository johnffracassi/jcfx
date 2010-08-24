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
	
	protected void setValue(Object value) {
		super.setValue(value);
		if(value instanceof Double) {
			this.value = (Double)value;
		}
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
