package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class AndNode<M> extends LogicNode<M>
{
    public AndNode()
    {
        super();
    }
    
    public AndNode(Node<M> parent, Node<M> left, Node<M> right)
    {
        super(parent, left, right);
    }

    @Override
    public boolean evaluate(M model)
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
