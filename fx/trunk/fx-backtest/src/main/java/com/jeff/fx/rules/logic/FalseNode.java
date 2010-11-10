package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.AbstractLeafNode;

public class FalseNode<M> extends AbstractLeafNode<M>
{
    @Override
    public boolean evaluate(M model)
    {
        return false;
    }
}
