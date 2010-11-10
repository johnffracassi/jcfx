package com.jeff.fx.rules;

import com.jeff.fx.rules.Node;

public abstract class AbstractLeafNode<M> implements Node<M>
{
    @Override
    public int getChildCount()
    {
        return 0;
    }

    @Override
    public Node<M> getChild(int idx)
    {
        throw new IndexOutOfBoundsException("I'm a leaf node");
    }
}
