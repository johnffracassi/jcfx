package com.jeff.fx.gui.renderer;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class LocalDateCellRenderer extends DefaultTableCellRenderer  {

	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
	
	public LocalDateCellRenderer() {
		setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	protected void setValue(Object value) {
		if(value instanceof LocalDate) {
			setText(dtf.print((LocalDate)value));
		}
	}
}
