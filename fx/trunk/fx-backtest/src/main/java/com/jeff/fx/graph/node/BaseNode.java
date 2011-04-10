package com.jeff.fx.graph.node;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lookforward.CandleFilterModel;

import java.io.Serializable;

public class BaseNode implements Serializable
{
    private String label;
    private BaseNode leftChild;
    private BaseNode rightChild;

    public BaseNode(String label)
    {
        this.label = label;
    }

    public void setChildNode(boolean value, BaseNode node)
    {
        if(value)
        {
            this.leftChild = node;
        }
        else
        {
            this.rightChild = node;
        }
    }

    public BaseNode getChild(boolean result)
    {
        return result ? leftChild : rightChild;
    }

    public boolean evaluate(CandleDataPoint candle, CandleFilterModel model)
    {
        return (true);
    }

    public void visit()
    {
        System.out.println("Visiting node: " + getClass().getSimpleName());
    }

    @Override
    public String toString()
    {
        return label;
    }

    public String getLabel()
    {
        return label;
    }

    public boolean isTerminal()
    {
        return leftChild == null && rightChild == null;
    }
}
