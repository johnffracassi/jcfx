package com.jeff.fx.lookforward;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;

public class CandleDataPointList implements List<CandleDataPoint>
{
    private CandleCollection candles;
    private int head;

    public CandleDataPointList(CandleCollection candles, int head)
    {
        this.candles = candles;
        this.head = head;
    }
    
    public void setHeadTo(int index)
    {
        this.head = index;
    }
    
    @Override
    public int size()
    {
        return head;
    }

    @Override
    public boolean isEmpty()
    {
        return candles.getCandleCount() > 0;
    }

    @Override
    public boolean contains(Object o)
    {
        throw new RuntimeException("not supported");
    }

    @Override
    public Iterator<CandleDataPoint> iterator()
    {
        return null;
    }

    @Override
    public Object[] toArray()
    {
        throw new RuntimeException("not supported");
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        throw new RuntimeException("not supported");
    }

    @Override
    public boolean add(CandleDataPoint e)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public boolean remove(Object o)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends CandleDataPoint> c)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public boolean addAll(int index, Collection<? extends CandleDataPoint> c)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public void clear()
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public CandleDataPoint get(int index)
    {
        return candles.getCandle(head - index);
    }

    @Override
    public CandleDataPoint set(int index, CandleDataPoint element)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public void add(int index, CandleDataPoint element)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public CandleDataPoint remove(int index)
    {
        throw new RuntimeException("list is immutable");
    }

    @Override
    public int indexOf(Object o)
    {
        throw new RuntimeException("not supported");
    }

    @Override
    public int lastIndexOf(Object o)
    {
        throw new RuntimeException("not supported");
    }

    @Override
    public ListIterator<CandleDataPoint> listIterator()
    {
        return null;
    }

    @Override
    public ListIterator<CandleDataPoint> listIterator(int index)
    {
        return null;
    }

    @Override
    public List<CandleDataPoint> subList(int fromIndex, int toIndex)
    {
        throw new RuntimeException("not supported");
    }
}