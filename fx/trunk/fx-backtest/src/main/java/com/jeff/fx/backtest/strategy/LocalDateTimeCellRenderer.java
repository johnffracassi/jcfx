package com.jeff.fx.backtest.strategy;

import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class LocalDateTimeCellRenderer extends DefaultTableCellRenderer {
	public LocalDateTimeCellRenderer() {
	}

	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	
	protected void setValue(Object value) {
		if(value instanceof LocalDateTime) {
			setText(dtf.print((LocalDateTime)value));
		}
	}
}
