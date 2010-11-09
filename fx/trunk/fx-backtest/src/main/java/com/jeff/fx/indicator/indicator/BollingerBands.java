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
public final class BollingerBands extends TAWrapper
{
    private int periods;
    private int devUp;
    private int devDown;
    
    public BollingerBands()
    {
        this(5, 2, 2);
    }
    
    public BollingerBands(int periods, int devUp, int devDown)
    {
        this.periods = periods;
        this.devUp = devUp;
        this.devDown = devDown;
    }
    
    public void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][])
    {
        core.bbands(0, close.length-1, close, periods, devUp, devDown, MAType.Sma, startIdx, new MInteger(), values[0], values[1], values[2]);
    }
    
    @Override
    public String getKey()
    {
        return "bb";
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
