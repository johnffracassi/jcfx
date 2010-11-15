package com.jeff.fx.rules.business;

import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;

public class ELNode<T> implements Node<T>
{
    private String expression;
    protected Comparable<?> value;
    protected Operand operand;
    
    public ELNode(String expression, Operand operand, Comparable<?> value)
    {
        this.expression = expression;
        this.value = value;
        this.operand = operand;
    }

    public boolean evaluate(T model) 
    {
        return false;
    }

    @Override
    public int getChildCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Node<T> getChild(int idx)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getChildIndex(Node<T> node)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setChild(int idx, Node<T> node)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Node<T> getParent()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParent(Node<T> parent)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getLabel()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDescription()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
