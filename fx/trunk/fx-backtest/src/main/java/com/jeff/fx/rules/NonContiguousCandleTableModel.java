package com.jeff.fx.rules;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.jeff.fx.backtest.dataviewer.ColumnDescriptor;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.gui.renderer.DayOfWeekCellRenderer;
import com.jeff.fx.gui.renderer.LocalDateCellRenderer;
import com.jeff.fx.gui.renderer.LocalTimeCellRenderer;
import com.jeff.fx.gui.renderer.PercentageChangeCellRenderer;
import com.jeff.fx.gui.renderer.PriceCellRenderer;

/**
 * TODO merge with CandleTableModel?
 */
public class NonContiguousCandleTableModel extends DefaultTableModel
{
    private List<CandleDataPoint> data;
    
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
        
        switch(col) {
            case 0: return row+1;
            case 1: return data.get(row).getDateTime().toLocalDate();
            case 2: return data.get(row).getDateTime().getDayOfWeek();
            case 3: return data.get(row).getDateTime().toLocalTime();
            case 4: return data.get(row).getBuyOpen();
            case 5: return data.get(row).getBuyHigh();
            case 6: return data.get(row).getBuyLow();
            case 7: return data.get(row).getBuyClose();
            case 8: return data.get(row).getBuyVolume();
            case 9: return data.get(row).getChangePercentage();
            case 10: return data.get(row).getRange();
            case 11: return data.get(row).getChange();
            default: return "X";
        }
    }

    public void update(List<CandleDataPoint> candles) {
        this.data = candles;
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
        return data.size();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public TableCellRenderer getRenderer(int col) {
        return columns[col].getRenderer();
    }
}
