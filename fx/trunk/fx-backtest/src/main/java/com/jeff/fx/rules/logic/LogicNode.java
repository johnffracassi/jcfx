package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

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
    
    @Override
    public int getChildCount()
    {
        return 2;
    }
    
    @Override
    public Node<M> getChild(int idx)
    {
        if(idx == 0)
        {
            return left;
        }
        else if(idx == 1)
        {
            return right;
        }
        
        throw new IndexOutOfBoundsException("Logic node only has 2 children");
    }
    
    @Override
    public String getDescription()
    {
        return getLabel();
    }

}
