package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class XorNode extends LogicNode
{
    public XorNode()
    {
        super();
    }

    public XorNode(Node parent, Node left, Node right)
    {
        super(parent, left, right);
    }

    @Override
    public boolean evaluate(Object model)
    {
        boolean leftValue = left.evaluate(model);
        boolean rightValue = right.evaluate(model);
        
        return (leftValue || rightValue) && !(leftValue && rightValue);
    }

    @Override
    public String getLabel()
    {
        return "Xor";
    }
}
