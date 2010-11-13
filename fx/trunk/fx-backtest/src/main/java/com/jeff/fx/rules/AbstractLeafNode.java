package com.jeff.fx.rules;

import com.jeff.fx.rules.Node;

public abstract class AbstractLeafNode<M> implements Node<M>
{
    private Node<M> parent;
    
    public AbstractLeafNode()
    {
    }
    
    public AbstractLeafNode(Node<M> parent)
    {
        this.parent = parent;
    }
    
    @Override
    public int getChildCount()
    {
        return 0;
    }
    
    @Override
    public void setChild(int idx, Node<M> node)
    {
        throw new IndexOutOfBoundsException("I'm a leaf node, don't set child nodes on me");
    }

    @Override
    public Node<M> getChild(int idx)
    {
        throw new IndexOutOfBoundsException("I'm a leaf node, I have no children");
    }
    
    @Override
    public int getChildIndex(Node<M> node)
    {
        throw new RuntimeException("Leaf node has no children!");
    }
    
    @Override
    public String toString()
    {
        return getLabel();
    }

    public Node<M> getParent()
    {
        return parent;
    }

    public void setParent(Node<M> parent)
    {
        this.parent = parent;
    }
}
