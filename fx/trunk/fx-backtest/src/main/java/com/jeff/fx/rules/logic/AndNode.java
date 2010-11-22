package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class AndNode extends LogicNode
{
    public AndNode()
    {
        super();
    }
    
    public AndNode(Node parent, Node left, Node right)
    {
        super(parent, left, right);
    }

    @Override
    public boolean evaluate(Object model)
    {
        boolean leftValue = left.evaluate(model);
        
        if(!leftValue)
        {
            return false;
        }
        
        boolean rightValue = right.evaluate(model);
        
        return rightValue;
    }

    @Override
    public String getLabel()
    {
        return "And";
    }
}
