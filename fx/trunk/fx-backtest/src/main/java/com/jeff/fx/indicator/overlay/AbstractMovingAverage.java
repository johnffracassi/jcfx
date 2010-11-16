package com.jeff.fx.indicator.overlay;

import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.gui.beanform.Label;
import com.jeff.fx.gui.beanform.Property;
import com.jeff.fx.indicator.AbstractIndicator;

public abstract class AbstractMovingAverage extends AbstractIndicator
{
    @Property(key = "ma.cvm")
    @Label("Price Model")
    private CandleValueModel model;

    public AbstractMovingAverage()
    {
        this(14, CandleValueModel.Typical);
    }

    public AbstractMovingAverage(int periods, CandleValueModel cvm)
    {
        super(periods);
        this.model = cvm;
    }

    public void setParams(Object... params)
    {
        if(params.length > 0)
        {
            int periods = (new Integer(String.valueOf(params[0])));
            setPeriods(periods);
        }
        
        if(params.length > 1)
        {
            model = CandleValueModel.valueOf(String.valueOf(params[1]));
        }
    }

    public final float getSlope(int idx, int countBack)
    {
        return getValue(idx) - getValue(idx - countBack);
    }

    public final int getDirection(int idx)
    {
        float diff = getSlope(idx, 5);
        return diff > 0.00005 ? 1 : diff < 0.00005 ? -1 : 0;
    }

    public String getDisplayName()
    {
        return getKey() + "(" + getPeriods() + "," + model + ")";
    }

    public CandleValueModel getModel()
    {
        return model;
    }

    public void setModel(CandleValueModel model)
    {
        this.model = model;
    }
}
