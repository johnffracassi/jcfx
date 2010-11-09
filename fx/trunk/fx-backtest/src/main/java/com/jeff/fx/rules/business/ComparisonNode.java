package com.jeff.fx.rules.business;

import com.jeff.fx.rules.MockModel;
import com.jeff.fx.rules.Node;

public class ComparisonNode implements Node<MockModel>
{
    private Comparable value;
    private Operand operand;
    
    public ComparisonNode(Operand operand, Comparable value)
    {
        this.value = value;
        this.operand = operand;
    }
    
    @Override
    public boolean evaluate(MockModel model)
    {
        return operand.evaluate(model.getNumber(), value);
    }
}
