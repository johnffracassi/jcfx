package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.AbstractLeafNode;
import com.jeff.fx.rules.Node;

public class TrueNode<M> extends AbstractLeafNode<M>
{
    @Override
    public boolean evaluate(M model)
    {
        return true;
    }
}
