package com.jeff.fx.backtest.dataviewer;

import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.jeff.fx.common.CandleWeek;

@SuppressWarnings("serial")
public class CandleDataTableModel extends DefaultTableModel {

	private CandleWeek data;
	private LocalDateTime start;
	private LocalDateTime end;
	private int startIdx;
	private int endIdx;
	
	private ColumnDescriptor[] columns = new ColumnDescriptor[] {
		new ColumnDescriptor("Idx", Integer.class),
		new ColumnDescriptor("Date", LocalDate.class),
		new ColumnDescriptor("Day", Integer.class),
		new ColumnDescriptor("Time", LocalTime.class),
		new ColumnDescriptor("Open", Double.class),
		new ColumnDescriptor("High", Double.class),
		new ColumnDescriptor("Low", Double.class),
		new ColumnDescriptor("Close", Double.class),
		new ColumnDescriptor("Vol", Integer.class),
		new ColumnDescriptor("Change", Double.class),
		new ColumnDescriptor("Range", Integer.class),
		new ColumnDescriptor("Size", Integer.class)
	};

	public Object getValueAt(int row, int col) {
		
		row += startIdx;
		
		switch(col) {
			case 0: return row+1;
			case 1: return data.getCandle(row).getDate().toLocalDate();
			case 2: return data.getCandle(row).getDate().getDayOfWeek();
			case 3: return data.getCandle(row).getDate().toLocalTime();
			case 4: return data.getCandle(row).getBuyOpen();
			case 5: return data.getCandle(row).getBuyHigh();
			case 6: return data.getCandle(row).getBuyLow();
			case 7: return data.getCandle(row).getBuyClose();
			case 8: return data.getCandle(row).getBuyVolume();
			case 9: return data.getCandle(row).getChangePercentage();
			case 10: return data.getCandle(row).getRange();
			case 11: return data.getCandle(row).getSize();
			default: return "X";
		}
	}

	public void update(CandleWeek candles, LocalDateTime start, LocalDateTime end) {
		this.data = candles;
		this.start = start;
		this.end = end;
		startIdx = candles.getCandleIndex(start);
		endIdx = candles.getCandleIndex(end);
		fireTableDataChanged();
	}
	
	public void update(CandleWeek candles) {
		this.data = candles;
		this.start = candles.getStartDate().toLocalDateTime(LocalTime.MIDNIGHT);
		this.end = candles.getStartDate().plusDays(7).toLocalDateTime(LocalTime.MIDNIGHT).minusMinutes(1);
		startIdx = 0;
		endIdx = candles.getCandleCount();
		fireTableDataChanged();
	}
	
	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int col) {
		return columns[col].getType();
	}
	
	public String getColumnName(int col) {
		return columns[col].getName();
	}

	public int getRowCount() {
		if(data == null) {
			return 0;
		}
		return (endIdx - startIdx);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
}
