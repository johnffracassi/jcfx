package com.jeff.fx.gui;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class PercentageChangeCellRenderer extends PriceCellRenderer {

	private static final Color POSITIVE = new Color(0, 128, 0);
	private static final Color NEGATIVE = new Color(208, 0, 0);
	private static final Color NONE = Color.BLACK;
	
	private double value = 0.0;
	
	public PercentageChangeCellRenderer(int dp) {
		super("%+." + dp + "f%%");
		setHorizontalAlignment(RIGHT);
	}
	
	protected void setValue(Object value) {
		if(value instanceof Double) {
			this.value = (Double)value * 100.0;
			super.setValue(this.value);
		} else {
			super.setValue(value);
		}
	}
	
	public void paint(Graphics g) {
		if(value > 0.0) {
			setForeground(POSITIVE);
		} else if(value < 0.0) {
			setForeground(NEGATIVE);
		} else {
			setForeground(NONE);
		}
		
		super.paint(g);
	}
	
	public static void main(String[] args) {
		System.out.printf("%+.2f%%", 0.1391513);
	}
}
