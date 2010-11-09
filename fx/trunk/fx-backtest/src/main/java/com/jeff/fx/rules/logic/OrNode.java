package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class OrNode<M> extends LogicNode<M>
{
    public OrNode(Node<M> left, Node<M> right)
    {
        super(left, right);
    }

    @Override
    public boolean evaluate(M model)
    {
        boolean leftValue = left.evaluate(model);
        
        if(leftValue)
        {
            return true;
        }
        
        boolean rightValue = right.evaluate(model);
        
        return rightValue;
    }
}
