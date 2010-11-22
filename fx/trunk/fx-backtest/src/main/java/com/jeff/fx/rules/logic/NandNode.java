package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class NandNode extends LogicNode
{
    public NandNode()
    {
        super();
    }
    
    public NandNode(Node parent, Node left, Node right)
    {
        super(parent, left, right);
    }

    @Override
    public boolean evaluate(Object model)
    {
        boolean leftValue = left.evaluate(model);
        
        if(!leftValue)
        {
            return true;
        }
        
        return !right.evaluate(model);
    }

    @Override
    public String getLabel()
    {
        return "Nand";
    }
}
