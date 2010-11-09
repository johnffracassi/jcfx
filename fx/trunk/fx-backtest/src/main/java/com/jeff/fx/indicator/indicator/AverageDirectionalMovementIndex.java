package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.TAWrapper;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

@Component
@ChartType(ChartTypes.Oscillator)
public final class AverageDirectionalMovementIndex extends TAWrapper
{
    private int periods;
    
    public AverageDirectionalMovementIndex()
    {
        this.periods = 14;
    }
    
    public AverageDirectionalMovementIndex(int periods)
    {
        this.periods = periods;
    }
    
    public void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][])
    {
        core.adx(0, close.length-1, high, low, close, periods, startIdx, new MInteger(), values[0]);
    }
    
    @Override
    public String getKey()
    {
        return "adx";
    }
    
    @Override
    public String getDisplayName()
    {
        return getKey() + "(" + periods + ")";
    }
    
    @Override
    public void setParams(Object... params)
    {
    }

}
