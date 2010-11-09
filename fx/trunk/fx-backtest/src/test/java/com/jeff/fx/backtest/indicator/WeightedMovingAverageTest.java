package com.jeff.fx.backtest.indicator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jeff.fx.indicator.FixedSizeNumberQueue2;
import com.jeff.fx.indicator.overlay.WeightedMovingAverage;

public class WeightedMovingAverageTest 
{
	private WeightedMovingAverage wma;
	
	private static final float ERR = 0.00001f;
	
    @Test
    public void simplestEmaTest()
    {
        float[] weights = { 1.0f };
        
        FixedSizeNumberQueue2 q = new FixedSizeNumberQueue2(1);
        q.add(1.0f);
        
        assertEquals(1.0f, q.weightedAverage(weights), ERR);
    }
    
    @Test
    public void simpleEmaTestConstantValues()
    {
        float[] weights = { 1.0f, 0.75f, 0.5f, 0.25f };
        
        FixedSizeNumberQueue2 q = new FixedSizeNumberQueue2(4);
        q.add(1.0f);
        q.add(1.0f);
        q.add(1.0f);
        q.add(1.0f);
        
        assertEquals(1.0f, q.weightedAverage(weights), ERR);
    }
    
    @Test
    public void simpleEmaTest2()
    {
        float[] weights = { 1.0f, 0.5f };
        
        FixedSizeNumberQueue2 q = new FixedSizeNumberQueue2(2);
        q.add(1.0f);
        q.add(2.0f);
        
        assertEquals(2.0f, q.weightedAverage(weights), ERR);
    }
    
	
}
