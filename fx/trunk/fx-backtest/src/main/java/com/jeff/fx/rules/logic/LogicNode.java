package com.jeff.fx.rules.logic;

import com.jeff.fx.rules.Node;

public abstract class LogicNode implements Node
{
    protected Node parent;
    protected Node left;
    protected Node right;
    
    public LogicNode()
    {
        left = new TrueNode(this);
        right = new TrueNode(this);
    }
    
    public LogicNode(Node parent, Node left, Node right)
    {
        this.parent = parent;
        this.left = left;
        this.right = right;
        left.setParent(this);
        right.setParent(this);
    }
    
    public abstract boolean evaluate(Object model); 
    
    @Override
    public int getChildCount()
    {
        return 2;
    }
    
    @Override
    public void setChild(int idx, Node node)
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
    public Node getChild(int idx)
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
    public int getChildIndex(Node node)
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
    public Node getParent()
    {
        return parent;
    }
    
    @Override
    public void setParent(Node parent)
    {
        this.parent = parent;
    }
}
