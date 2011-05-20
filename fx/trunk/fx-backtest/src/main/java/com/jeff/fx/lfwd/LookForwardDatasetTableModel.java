package com.jeff.fx.lfwd;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import org.joda.time.LocalDateTime;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LookForwardDatasetTableModel extends DefaultTableModel
{
    public List<List<CandleDataPoint>> dataset;
    private CandleValueModel cvm = CandleValueModel.Close;

    @Override
    public int getRowCount() {
        if(dataset == null)
            return 0;

        return dataset.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "Open Time";
            case 1: return "Price[0]";
            default: return String.format("Price[%d]", (int)Math.pow(2, column - 2));
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
            case 1: return cvm.evaluate(candles.get(0));
            default: return (int)((cvm.evaluate(candles.get((int)Math.pow(2, column - 2))) - cvm.evaluate(candles.get(0)))  * 10000);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0: return LocalDateTime.class;
            default: return Integer.class;
        }
    }

    public void setCollections(List<List<CandleDataPoint>> collections) {
        dataset = collections;
        fireTableDataChanged();
    }
}
