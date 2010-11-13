package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.AbstractLeafNode;
import com.jeff.fx.rules.Node;

public class FalseNode extends AbstractLeafNode
{
    public FalseNode()
    {
        super();
    }
    
    public FalseNode(Node parent)
    {
        super(parent);
    }

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
