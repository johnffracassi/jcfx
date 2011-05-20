package com.jeff.fx.lfwd;

import com.jeff.fx.common.CandleDataPoint;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class LookForwardDatasetController {
    
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
