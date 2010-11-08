package com.jeff.fx.rules;

public class TrueNode<M> implements Node<M>
{
    @Override
    public boolean evaluate(M model)
    {
        return true;
    }
}
