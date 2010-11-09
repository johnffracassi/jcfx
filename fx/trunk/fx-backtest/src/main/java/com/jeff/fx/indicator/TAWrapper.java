package com.jeff.fx.indicator;

import com.jeff.fx.common.CandleCollection;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;

public abstract class TAWrapper implements Indicator
{
    private CoreAnnotated talib = new CoreAnnotated();

    private double[][] values;
    private boolean calculated = false;
    private int startIdx;

    public abstract void calculate(Core core, float[] open, float[] high, float[] low, float[] close, MInteger startIdx, double values[][]);
    public abstract String getKey();
    
    @Override
    public final void calculate(CandleCollection candles)
    {
        float[] open = (candles.getRawValues(0));
        float[] high = (candles.getRawValues(1));
        float[] low = (candles.getRawValues(2));
        float[] close = (candles.getRawValues(3));

        values = new double[getResultCount()][close.length];
        
        MInteger startIdx = new MInteger();

        calculate(talib, open, high, low, close, startIdx, values);
        
        this.startIdx = startIdx.value;
        
        calculated = true;
    }

    public int getResultCount()
    {
        return 1;
    }
    
    @Override
    public String getDisplayName()
    {
        return "TA CCI";
    }

    @Override
    public float getValue(int result, int idx)
    {
        if(calculated)
        {
            if(idx < startIdx)
            {
                return Float.NaN;
            }
            else
            {
                return (float)values[result][idx - startIdx];
            }
        }
        
        return Float.NaN;
    }

    public float getValue(int idx)
    {
        return getValue(0, idx); 
    }

    @Override
    public boolean requiresCalculation()
    {
        return !calculated;
    }

    @Override
    public void invalidate()
    {
        calculated = false;
    }
    
    @Override
    public int getSize()
    {
        return values.length;
    }

    @Override
    public void setParams(Object... params)
    {
    }
}
