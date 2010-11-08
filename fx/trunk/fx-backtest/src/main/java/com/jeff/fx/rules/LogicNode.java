package com.jeff.fx.rules;

public abstract class LogicNode<M> implements Node<M>
{
    protected Node<M> left;
    protected Node<M> right;
    
    public LogicNode(Node<M> left, Node<M> right)
    {
        this.left = left;
        this.right = right;
    }
    
    public abstract boolean evaluate(M model); 
}
