package com.jeff.fx.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class LocalTimeCellRenderer extends DefaultTableCellRenderer  {

	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
	
	public LocalTimeCellRenderer() {
		setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	protected void setValue(Object value) {
		if(value instanceof LocalTime) {
			setText(dtf.print((LocalTime)value));
		}
	}
}
