package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class NandNode<M> extends LogicNode<M>
{
    public NandNode(Node<M> left, Node<M> right)
    {
        super(left, right);
    }

    @Override
    public boolean evaluate(M model)
    {
        boolean leftValue = left.evaluate(model);
        
        if(!leftValue)
        {
            return true;
        }
        
        return !right.evaluate(model);
    }
}
