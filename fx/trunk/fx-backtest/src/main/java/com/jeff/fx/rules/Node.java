package com.jeff.fx.rules;

public interface Node
{
    boolean evaluate(Object model);
    int getChildCount();
    Node getParent();
    Node getChild(int idx);
    int getChildIndex(Node node);
    void setChild(int idx, Node node);
    void setParent(Node parent);
    String getLabel();
    String getDescription();
}
