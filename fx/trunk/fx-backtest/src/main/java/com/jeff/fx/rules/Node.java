package com.jeff.fx.rules;

public interface Node<M>
{
    boolean evaluate(M model);
    int getChildCount();
    Node<M> getChild(int idx);
    int getChildIndex(Node<M> node);
    void setChild(int idx, Node<M> node);
    Node<M> getParent();
    void setParent(Node<M> parent);
    String getLabel();
    String getDescription();
}
