package com.jeff.fx.filter;

import java.util.AbstractList;

/**
 * a reverse list for floats
 */
class IndicatorResultList extends AbstractList<Float>
{
    private float[] values;
    private int head;
    
    public IndicatorResultList(float[] values, int head)
    {
        this.head = head;
        this.values = values;
    }
    
    public void setHead(int head)
    {
        this.head = head;
    }
    
    @Override
    public Float get(int index)
    {
        if(index > head)
        {
            return null;
        }
        
        return values[head - index];
    }

    @Override
    public int size()
    {
        return head;
    }
}