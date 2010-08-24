package com.jeff.fx.backtest.dataviewer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.gui.renderer.DayOfWeekCellRenderer;
import com.jeff.fx.gui.renderer.LocalDateCellRenderer;
import com.jeff.fx.gui.renderer.LocalTimeCellRenderer;
import com.jeff.fx.gui.renderer.PercentageChangeCellRenderer;
import com.jeff.fx.gui.renderer.PriceCellRenderer;

@SuppressWarnings("serial")
public class CandleDataTableModel extends DefaultTableModel {

	private CandleWeek data;
	private LocalDateTime start;
	private LocalDateTime end;
	private int startIdx;
	private int endIdx;
	
	private ColumnDescriptor[] columns = new ColumnDescriptor[] {
		new ColumnDescriptor("Idx", Integer.class),
		new ColumnDescriptor("Date", LocalDate.class, new LocalDateCellRenderer()),
		new ColumnDescriptor("Day", Integer.class, new DayOfWeekCellRenderer()),
		new ColumnDescriptor("Time", LocalTime.class, new LocalTimeCellRenderer()),
		new ColumnDescriptor("Open", Double.class, new PriceCellRenderer(4)),
		new ColumnDescriptor("High", Double.class, new PriceCellRenderer(4)),
		new ColumnDescriptor("Low", Double.class, new PriceCellRenderer(4)),
		new ColumnDescriptor("Close", Double.class, new PriceCellRenderer(4)),
		new ColumnDescriptor("Vol", Integer.class),
		new ColumnDescriptor("Change", Double.class, new PercentageChangeCellRenderer(3)),
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
	
	public TableCellRenderer getRenderer(int col) {
		return columns[col].getRenderer();
	}
}
