package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class OrNode extends LogicNode
{
    public OrNode()
    {
        super();
    }
    
    public OrNode(Node parent, Node left, Node right)
    {
        super(parent, left, right);
    }

    @Override
    public boolean evaluate(Object model)
    {
        boolean leftValue = left.evaluate(model);
        
        if(leftValue)
        {
            return true;
        }
        
        boolean rightValue = right.evaluate(model);
        
        return rightValue;
    }

    @Override
    public String getLabel()
    {
        return "Or";
    }
}
