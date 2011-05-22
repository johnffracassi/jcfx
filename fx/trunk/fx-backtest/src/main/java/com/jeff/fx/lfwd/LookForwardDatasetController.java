package com.jeff.fx.lfwd;

import com.jeff.fx.backtest.chart.candle.CandleChartFactory;
import com.jeff.fx.common.CandleDataPoint;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class LookForwardDatasetController
{
    @Autowired
    private CandleChartFactory chartFactory;

    private LookForwardDatasetView view;
    private LookForwardDatasetTableModel model;

    public LookForwardDatasetController()
    {
        view = new LookForwardDatasetView();
        model = new LookForwardDatasetTableModel();
    }

    @PostConstruct
    private void init()
    {
        view.getTable().setModel(model);
        view.getTable().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    LocalDateTime dateTime = (LocalDateTime)view.getTable().getValueAt(view.getTable().getSelectedRow(), 0);
                    chartFactory.showChart();
                }
            }
        });
    }

    public void setCollections(List<List<CandleDataPoint>> collections)
    {
        model.setCollections(collections);
    }

    public LookForwardDatasetView getView() {
        return view;
    }

    public LookForwardDatasetTableModel getModel() {
        return model;
    }
}
