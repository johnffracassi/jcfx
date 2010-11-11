package com.jeff.fx.rules;

public interface Node<M>
{
    boolean evaluate(M model);
    int getChildCount();
    Node<M> getChild(int idx);
    String getLabel();
    String getDescription();
}
