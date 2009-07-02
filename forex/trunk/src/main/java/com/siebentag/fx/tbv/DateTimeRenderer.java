package com.siebentag.fx.tbv;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class DateTimeRenderer extends DefaultTableCellRenderer
{
	private static final String dateFormat = "dd-MMM-yy";
	private static final String timeFormat = "HH:mm";
	private static final String dateTimeFormat = dateFormat + " " + timeFormat;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
	
	protected void setValue(Object value) 
	{
		if(value instanceof LocalDateTime)
		{
			setText(((LocalDateTime)value).toString(dateTimeFormat));
		}
		else if(value instanceof LocalDate)
		{
			setText(((LocalDate)value).toString(dateFormat));
		}
		else if(value instanceof LocalTime)
		{
			setText(((LocalTime)value).toString(timeFormat));
		}
		else if(value instanceof Date)
		{
			setText(sdf.format((Date)value));
		}
	}
}