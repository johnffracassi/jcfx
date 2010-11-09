package com.jeff.fx.rules.business;

import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;

public abstract class ComparisonNode<T> implements Node<T>
{
    protected Comparable<?> value;
    protected Operand operand;
    
    public ComparisonNode(Operand operand, Comparable<?> value)
    {
        this.value = value;
        this.operand = operand;
    }
    
    public abstract boolean evaluate(T model);
}
