package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.TAWrapper;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

@Component
@ChartType(ChartTypes.Oscillator)
public class CommodityChannelIndex extends TAWrapper
{
    private int periods;
    
    public CommodityChannelIndex()
    {
        this(14);
    }
    
    public CommodityChannelIndex(int periods)
    {
        this.periods = periods;
    }

    public void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][])
    {
        core.cci(0, close.length-1, high, low, close, periods, startIdx, new MInteger(), values[0]);
    }

    @Override
    public String getKey()
    {
        return "cci";
    }

    @Override
    public String getDisplayName()
    {
        return "cci(" + periods + ")";
    }

    @Override
    public void setParams(Object... params)
    {
    }
}
