package com.jeff.fx.datamanager;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

public class DataManagerCellRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 8948809576458018478L;

	private static final Color pos = new Color(0, 128, 0);
	private static final Color neg = new Color(128, 0, 0);

	private int dp;
	private boolean colouring;
	public double value;

	public DataManagerCellRenderer(int dp, boolean colouring) {
		this.dp = dp;
		this.colouring = colouring;
	}

	@Override
	public void paint(Graphics g) {
		if (colouring) {
			if (value > 0.0) {
				setForeground(pos);
			} else if (value < 0.0) {
				setForeground(neg);
			}
		} else {
			setForeground(Color.BLACK);
		}

		super.paint(g);
	}

	protected void setValue(Object value) {
		setHorizontalAlignment(RIGHT);

		if (value instanceof Integer) {
			setText(String.valueOf(value));
		} else {
			this.value = (Double) value;
			setText(String.format("%." + dp + "f", this.value));
		}
	}
}