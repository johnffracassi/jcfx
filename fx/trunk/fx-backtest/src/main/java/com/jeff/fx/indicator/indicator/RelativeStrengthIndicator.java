package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.TAWrapper;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

@Component
@ChartType(ChartTypes.Oscillator)
public class RelativeStrengthIndicator extends TAWrapper
{
    private int periods;
    
    public RelativeStrengthIndicator()
    {
        this(14);
    }
    
    public RelativeStrengthIndicator(int periods)
    {
        this.periods = periods;
    }

    public void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][])
    {
        core.rsi(0, close.length-1, close, periods, startIdx, new MInteger(), values[0]);
    }

    @Override
    public String getKey()
    {
        return "rsi";
    }

    @Override
    public String getDisplayName()
    {
        return "rsi(" + periods + ")";
    }

    @Override
    public void setParams(Object... params)
    {
    }
}
