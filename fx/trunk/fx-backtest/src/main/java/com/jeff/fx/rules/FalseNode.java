package com.jeff.fx.rules;

public class FalseNode<M> implements Node<M>
{
    @Override
    public boolean evaluate(M model)
    {
        return false;
    }
}
