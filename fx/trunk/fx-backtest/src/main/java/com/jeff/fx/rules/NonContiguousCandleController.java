package com.jeff.fx.rules;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.lfwd.CandleFilterProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NonContiguousCandleController
{
    @Autowired
    private CandleDataStore loader;

    @Autowired
    private CandleFilterProcessor processor;
    
    private NonContiguousCandleView view;
    private NonContiguousCandleTableModel model = new NonContiguousCandleTableModel();
    
    public NonContiguousCandleController()
    {
        view = new NonContiguousCandleView();
        view.getTable().setModel(model);
        
        view.getBtnApply().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apply();
            }
        });
    }
    
    public void update(List<CandleDataPoint> candles)
    {
        model.update(candles);
        view.getLblStatus().setText("Found " + candles.size() + " candle(s)");
    }
    
    private void apply()
    {
        // load candle dataset
        
        // apply lfwd
    }
}
