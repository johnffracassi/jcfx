package com.jeff.fx.filter;

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
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "Open Time";
            case 1: return "Price[0]";
            case 2: return "Price[5]";
            case 3: return "Price[10]";
            case 4: return "Price[15]";
            case 5: return "Price[30]";
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
            case 1: return cvm.evaluate(candles.get(0));
            case 2: return (int)((cvm.evaluate(candles.get(5)) - cvm.evaluate(candles.get(0)))  * 10000);
            case 3: return (int)((cvm.evaluate(candles.get(10)) - cvm.evaluate(candles.get(0))) * 10000);
            case 4: return (int)((cvm.evaluate(candles.get(15)) - cvm.evaluate(candles.get(0))) * 10000);
            case 5: return (int)((cvm.evaluate(candles.get(30)) - cvm.evaluate(candles.get(0))) * 10000);
            default: return "XXX";
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
