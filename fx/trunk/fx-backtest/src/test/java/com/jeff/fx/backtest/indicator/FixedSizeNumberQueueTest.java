package com.jeff.fx.backtest.indicator;

import org.junit.Test;

import com.jeff.fx.indicator.FixedSizeNumberQueue2;
import static org.junit.Assert.*;

public class FixedSizeNumberQueueTest 
{
    private FixedSizeNumberQueue2 queue;
	
	private static final float ERR = 0.00001f;
	
    @Test
    public void weightAverageTestConstantWeightAndValue()
    {
        queue = new FixedSizeNumberQueue2(10);
        
        for(int i=0; i<10; i++)
        {
            queue.add(1.0f);
        }
        
        float[] weights = { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f };
        
        assertEquals(1.0f, queue.average(), ERR);
        assertEquals(1.0f, queue.weightedAverage(weights), ERR);
    }
    
    @Test
    public void weightAverageTestVaryingWeightsConstantValue()
    {
        queue = new FixedSizeNumberQueue2(10);
        float[] weights = new float[10];
        
        for(int i=0; i<10; i++)
        {
            queue.add(1.0f);
            weights[i] = 1.0f - 0.1f * i;
        }
        
        assertEquals(1.0f, queue.average(), ERR);
        assertEquals(1.0f, queue.weightedAverage(weights), ERR);
    }
    
    @Test
    public void weightAverageTestVaryingWeightsVaryingValues()
    {
        queue = new FixedSizeNumberQueue2(3);
        float[] weights = { 1.0f, 0.75f, 0.5f };
        
        for(int i=0; i<10; i++)
        {
            queue.add(1.0f + i * 0.1f);
        }
        
        assertEquals(1.8000f, queue.average(), ERR);
        assertEquals(1.8222222f, queue.weightedAverage(weights), ERR);
    }
    
    
}
