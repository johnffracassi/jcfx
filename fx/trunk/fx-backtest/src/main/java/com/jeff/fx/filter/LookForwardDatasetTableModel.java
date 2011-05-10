package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import org.joda.time.LocalDateTime;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LookForwardDatasetTableModel extends DefaultTableModel
{
    public List<List<CandleDataPoint>> dataset;

    @Override
    public int getRowCount() {
        if(dataset == null)
            return 0;

        return dataset.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "Open Time";
            case 1: return "Price[0]";
            case 2: return "Price[2]";
            case 3: return "Price[4]";
            case 4: return "Price[8]";
            case 5: return "Price[16]";
            default: return "XXX";
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        List<CandleDataPoint> candles = dataset.get(row);
        switch(column) {
            case 0: return candles.get(0).getDateTime();
            case 1: return candles.get(0).getOpen();
            case 2: return (candles.get(2).getOpen() - candles.get(0).getOpen()) * 10000.0;
            case 3: return (candles.get(4).getOpen() - candles.get(0).getOpen()) * 10000.0;
            case 4: return (candles.get(8).getOpen() - candles.get(0).getOpen()) * 10000.0;
            case 5: return (candles.get(16).getOpen() - candles.get(0).getOpen()) * 10000.0;
            default: return "XXX";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0: return LocalDateTime.class;
            default: return Double.class;
        }
    }

    public void setCollections(List<List<CandleDataPoint>> collections) {
        dataset = collections;
        fireTableDataChanged();
    }
}
