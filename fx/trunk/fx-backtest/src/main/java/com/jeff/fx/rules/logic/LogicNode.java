package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public abstract class LogicNode<M> implements Node<M>
{
    protected Node<M> parent;
    protected Node<M> left;
    protected Node<M> right;
    
    public LogicNode()
    {
        left = new TrueNode(this);
        right = new TrueNode(this);
    }
    
    public LogicNode(Node<M> parent, Node<M> left, Node<M> right)
    {
        this.parent = parent;
        this.left = left;
        this.right = right;
        left.setParent(this);
        right.setParent(this);
    }
    
    public abstract boolean evaluate(M model); 
    
    @Override
    public int getChildCount()
    {
        return 2;
    }
    
    @Override
    public void setChild(int idx, Node<M> node)
    {
        if(idx == 0)
        {
            left = node;
        }
        else if(idx == 1)
        {
            right = node;
        }
        else
        {
            throw new IndexOutOfBoundsException("Logic node has exactly 2 children (invalid idx = " + idx + ")");
        }
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
    public int getChildIndex(Node<M> node)
    {
        if(node == left)
        {
            return 0;
        }
        else if(node == right)
        {
            return 1;
        }
        
        throw new RuntimeException("Node is not a child");
    }
    
    @Override
    public String getDescription()
    {
        return getLabel();
    }
    
    @Override
    public String toString()
    {
        return getLabel();
    }

    @Override
    public Node<M> getParent()
    {
        return parent;
    }
    
    @Override
    public void setParent(Node<M> parent)
    {
        this.parent = parent;
    }
}
