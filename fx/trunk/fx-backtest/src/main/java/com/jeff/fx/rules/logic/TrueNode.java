package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.AbstractLeafNode;
import com.jeff.fx.rules.Node;

public class TrueNode extends AbstractLeafNode
{
    public TrueNode(Node parent)
    {
        super(parent);
    }
    
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
