package com.jeff.fx.lfwd;

import com.jeff.fx.backtest.DatasetDefinitionPanel;
import com.jeff.fx.backtest.chart.candle.CandleChartFactory;
import com.jeff.fx.backtest.chart.candle.MiniDataLoader;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

@Component
public class LookForwardDatasetController
{
    @Autowired
    private CandleChartFactory chartFactory;

    @Autowired
    private MiniDataLoader dataLoader;

    @Autowired
    private DatasetDefinitionPanel datasetDefinitionPanel;

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
                    List<CandleDataPoint> candles = null;
                    try
                    {
                        FXDataRequest request = datasetDefinitionPanel.getRequest();
                        candles = dataLoader.load(request.getDataSource(), request.getInstrument(), request.getPeriod(), dateTime, 24);
                        chartFactory.showChart(candles, dateTime);
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
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
