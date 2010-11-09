package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public class FalseNode<M> implements Node<M>
{
    @Override
    public boolean evaluate(M model)
    {
        return false;
    }
}
