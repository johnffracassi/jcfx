package com.jeff.fx.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.jeff.fx.util.DateUtil;

@SuppressWarnings("serial")
public class DayOfWeekCellRenderer extends DefaultTableCellRenderer  {

	public DayOfWeekCellRenderer() {
		setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	protected void setValue(Object value) {
		if(value instanceof Integer) {
			setText(DateUtil.getDayOfWeek((Integer)value));
		}
	}
}
