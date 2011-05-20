package com.jeff.fx.graph.node;

import com.jeff.fx.gui.beanform.Label;
import com.jeff.fx.gui.beanform.Property;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.rules.Operand;

@Label("Expression Node")
public class ExpressionNode extends BaseNode
{
    @Property(key = "leftExpr")
    @Label("Expression 1")
    private String leftExpr = "candle.open";

    @Property(key = "op")
    @Label("Comparator")
    private Operand operand = Operand.ge;

    @Property(key = "rightExpr")
    @Label("Expression 2")
    private String rightExpr = "candle.close";

    public ExpressionNode()
    {
        super("Expression");
    }

    @Override
    public boolean evaluate(CandleFilterModel model, int idx)
    {
        Object lVal = model.evaluate(leftExpr);
        Object rVal = model.evaluate(rightExpr);

        return operand.evaluate((Comparable)lVal, (Comparable)rVal);
    }

    @Override
    public String getLabel()
    {
        return leftExpr + "\n" + operand.getLabel() + "\n" + rightExpr;
    }

    public String toString()
    {
        return getLabel();
    }

    public String getLeftExpr()
    {
        return leftExpr;
    }

    public void setLeftExpr(String leftExpr)
    {
        this.leftExpr = leftExpr;
    }

    public Operand getOperand()
    {
        return operand;
    }

    public void setOperand(Operand operand)
    {
        this.operand = operand;
    }

    public String getRightExpr()
    {
        return rightExpr;
    }

    public void setRightExpr(String rightExpr)
    {
        this.rightExpr = rightExpr;
    }
}
