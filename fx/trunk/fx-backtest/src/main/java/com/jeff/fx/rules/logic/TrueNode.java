package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.AbstractLeafNode;

public class TrueNode extends AbstractLeafNode
{
    @Override
    public boolean evaluate(Object model)
    {
        return true;
    }

    @Override
    public String getLabel()
    {
        return "True";
    }

    @Override
    public String getDescription()
    {
        return "True";
    }
}
