package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class XorNode<M> extends LogicNode<M>
{
    public XorNode(Node<M> left, Node<M> right)
    {
        super(left, right);
    }

    @Override
    public boolean evaluate(M model)
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
