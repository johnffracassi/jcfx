package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.TAWrapper;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

@Component
@ChartType(ChartTypes.Oscillator)
public class AverageTrueRange extends TAWrapper
{
    private int periods;
    
    public AverageTrueRange()
    {
        this.periods = 14;
    }
    
    public AverageTrueRange(int periods)
    {
        this.periods = periods;
    }

    public void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][])
    {
        core.atr(0, close.length-1, high, low, close, periods, startIdx, new MInteger(), values[0]);
    }

    @Override
    public String getKey()
    {
        return "atr";
    }

    @Override
    public String getDisplayName()
    {
        return "atr(" + periods + ")";
    }

    @Override
    public void setParams(Object... params)
    {
    }
}
