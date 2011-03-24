package com.jeff.fx.graph.node;

import com.jeff.fx.lookforward.CandleFilterModel;

import java.io.Serializable;

public class BaseNode implements Serializable
{
    private String label;
    private int maximumIncoming = Integer.MAX_VALUE;
    private int maximumOutgoing = 2;
    private int minimumIncoming = 0;
    private int minimumOutgoing = 0;

    public BaseNode(String label)
    {
        this.label = label;
    }

    public void visit(CandleFilterModel model)
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

    public void setLabel(String label)
    {
        this.label = label;
    }

    public int getMaximumIncoming()
    {
        return maximumIncoming;
    }

    public void setMaximumIncoming(int maximumIncoming)
    {
        this.maximumIncoming = maximumIncoming;
    }

    public int getMaximumOutgoing()
    {
        return maximumOutgoing;
    }

    public void setMaximumOutgoing(int maximumOutgoing)
    {
        this.maximumOutgoing = maximumOutgoing;
    }

    public int getMinimumIncoming()
    {
        return minimumIncoming;
    }

    public void setMinimumIncoming(int minimumIncoming)
    {
        this.minimumIncoming = minimumIncoming;
    }

    public int getMinimumOutgoing()
    {
        return minimumOutgoing;
    }

    public void setMinimumOutgoing(int minimumOutgoing)
    {
        this.minimumOutgoing = minimumOutgoing;
    }
}