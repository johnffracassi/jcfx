package com.jeff.fx.rules.business;

import com.jeff.fx.gui.beanform.Label;
import com.jeff.fx.gui.beanform.Property;
import com.jeff.fx.filter.CandleFilterModel;
import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;

public class ELNode extends AbstractFXNode
{
    @Property(key = "expr1")
    @Label("Expression 1")
    private String leftExpr;

    @Property(key = "op")
    @Label("Comparator")
    private Operand operand;
    
    @Property(key = "expr2")
    @Label("Expression 2")
    private String rightExpr;
    
    public ELNode()
    {
        this(null, "1.0", Operand.eq, "1.0");
    }

    public ELNode(String lexpr, Operand operand, String rexpr)
    {
        this();
        this.leftExpr = lexpr;
        this.operand = operand;
        this.rightExpr = rexpr;
    }

    public ELNode(Node parent, String lexpr, Operand operand, String rexpr)
    {
        super(parent);
        this.leftExpr = lexpr;
        this.operand = operand;
        this.rightExpr = rexpr;
    }

    public boolean evaluate(CandleFilterModel model) 
    {
        Object lVal = model.evaluate(leftExpr);
        Object rVal = model.evaluate(rightExpr);
        
        return operand.evaluate((Comparable)lVal, (Comparable)rVal);
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
