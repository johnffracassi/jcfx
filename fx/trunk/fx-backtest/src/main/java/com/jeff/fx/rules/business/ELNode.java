package com.jeff.fx.rules.business;

import com.jeff.fx.rules.AbstractLeafNode;
import com.jeff.fx.rules.Operand;

public class ELNode<T> extends AbstractLeafNode<T>
{
    private String expression;
    protected Comparable<?> value;
    protected Operand operand;
    
    public ELNode(String expression, Operand operand, Comparable<?> value)
    {
        this.expression = expression;
        this.operand = operand;
    }

    public boolean evaluate(T model) 
    {
        return false;
    }

    @Override
    public String getLabel()
    {
        return expression;
    }

    @Override
    public String getDescription()
    {
        return expression;
    }
}
