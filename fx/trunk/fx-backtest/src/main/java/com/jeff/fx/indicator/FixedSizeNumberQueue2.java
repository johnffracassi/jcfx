package com.jeff.fx.indicator;

public final class FixedSizeNumberQueue2 
{
    private static final long serialVersionUID = 6506626739170263513L;

    private float[] values;
    private int headIdx = 0;
    private float emaK = 0.0f;
    private float emaValue = 0.0f;
    
    private int capacity = 0;
    private int size = 0;
    private float sum = 0.0f;
    

    public FixedSizeNumberQueue2(int capacity)
    {
        this.capacity = capacity;
        values = new float[capacity];
        headIdx = 0;
        emaK = 2.0f / (capacity + 1);
    }

    private boolean isFull()
    {
        return (size == capacity);
    }

    private int remainingCapacity()
    {
        return capacity - size;
    }
    
    private int advanceHead()
    {
        headIdx = (headIdx + 1) % capacity;
        return headIdx;
    }
    
    public void add(float newValue)
    {
        // if the queue is bigger than capacity, remove first element from sum
        if(isFull())
        {
            sum -= values[headIdx];
        }
        
        // add new element to sum
        sum = sum + newValue;

        // if there's no more room then overwrite the queue head
        if (remainingCapacity() == 0)
        {
            values[headIdx] = newValue;
            advanceHead();
        }
        else
        {
            values[size] = newValue;
            size++;
        }
        
        // calculate ema
        if(!isFull())
        {
            emaValue = average();
        }
        else
        {
            emaValue = ((newValue - emaValue) * emaK) + emaK;
        }
    }

    public float ema()
    {
        return emaValue;
    }
    
    public float weightedAverage(float[] weights)
    {
        assert(weights != null);
        assert(weights.length == capacity);

        if(size == 0)
        {
            return 0.0f;
        }
        
        float sum = 0.0f;
        float sumOfWeights = 0.0f;
        for(int i=0; i<size; i++)
        {
            int idx = (capacity + (headIdx - 1) - i) % capacity;
            sum += (weights[i] * values[idx]);
            sumOfWeights += weights[i];
        }
        return sum / sumOfWeights;
    }
    
    public float average()
    {
        if (size == 0)
        {
            return 0.0f;
        }

        return sum / size;
    }

    private float headValue()
    {
        assert(headIdx >= 0);
        
        if(size == 0)
        {
            return Float.NaN;
        }
        else 
        {
            return values[headIdx];
        }
    }

    private float tailValue()
    {
        assert(headIdx >= 0);
        
        if(size == 0)
        {
            return Float.NaN;
        }
        else if(size == 1)
        {
            return values[0];
        }
        else
        {
            return values[(headIdx - 1) % capacity];
        }
    }

    public float direction()
    {
        return headValue() - tailValue();
    }
    
    public int capacity()
    {
        return capacity;
    }

    public float sum()
    {
        return sum;
    }
}
