package com.jeff.fx.rules.business;

import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.AbstractLeafNode;
import com.jeff.fx.rules.Operand;

public class ELNode extends AbstractLeafNode<CandleFilterModel>
{
    private String leftExpr;
    private Operand operand;
    private String rightExpr;
    
    public ELNode(String lexpr, Operand operand, String rexpr)
    {
        this.leftExpr = lexpr;
        this.operand = operand;
        this.rightExpr = rexpr;
    }

    public boolean evaluate(CandleFilterModel model) 
    {
        float lVal = model.evaluate(leftExpr, float.class);
        float rVal = model.evaluate(rightExpr, float.class);
        
        return operand.evaluate(lVal, rVal);
    }

    @Override
    public String getLabel()
    {
        return leftExpr + " " + operand.getLabel() + " " + rightExpr;
    }

    @Override
    public String getDescription()
    {
        return getLabel();
    }
}
