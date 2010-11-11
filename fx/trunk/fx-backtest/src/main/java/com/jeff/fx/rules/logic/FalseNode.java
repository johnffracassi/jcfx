package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.AbstractLeafNode;

public class FalseNode extends AbstractLeafNode
{
    @Override
    public boolean evaluate(Object model)
    {
        return false;
    }

    @Override
    public String getLabel()
    {
        return "False";
    }

    @Override
    public String getDescription()
    {
        return "False";
    }
}
