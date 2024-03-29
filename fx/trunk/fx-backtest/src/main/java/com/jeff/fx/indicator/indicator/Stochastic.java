package com.jeff.fx.indicator.indicator;

import org.springframework.stereotype.Component;

import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.TAWrapper;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;

@Component
@ChartType(ChartTypes.Oscillator)
public final class Stochastic extends TAWrapper
{
    private int fastK;
    private int slowK;
    private MAType maType;
    
    public Stochastic()
    {
        this(5, 2);
    }
    
    public Stochastic(int fastK, int slowK)
    {
        this.fastK = fastK;
        this.slowK = slowK;
    }
    
    public void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][])
    {
        core.stoch(0, close.length-1, high, low, close, fastK, slowK, maType, 5, maType, startIdx, new MInteger(), values[0], values[1]);
    }
    
    @Override
    public String getKey()
    {
        return "stoch";
    }
    
    @Override
    public String getDisplayName()
    {
        return getKey() + "(" + fastK + "," + slowK + ")";
    }
    
    @Override
    public void setParams(Object... params)
    {
    }
}
