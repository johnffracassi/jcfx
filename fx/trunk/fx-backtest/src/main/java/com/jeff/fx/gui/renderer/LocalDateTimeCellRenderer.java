package com.jeff.fx.gui.renderer;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class LocalDateTimeCellRenderer extends DefaultTableCellRenderer {
	
	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	
	public LocalDateTimeCellRenderer() {
		setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	protected void setValue(Object value) {
		if(value instanceof LocalDateTime) {
			setText(dtf.print((LocalDateTime)value));
		}
	}
}
