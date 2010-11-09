package com.jeff.fx.rules.business;


public enum Operand
{
    eq(new boolean[] { false, true, false }),
    ne(new boolean[] { true, false, true }), 
    gt(new boolean[] { false, false, true }),
    ge(new boolean[] { false, true, true }),
    lt(new boolean[] { true, false, false }), 
    le(new boolean[] { true, true, false });

    private boolean[] comparatorValues;
    
    private Operand(boolean[] comparatorValues)
    {
        this.comparatorValues = comparatorValues;
    }
    
    public boolean evaluate(Comparable o1, Comparable o2)
    {
        int result = o1.compareTo(o2);
        return comparatorValues[result + 1];
    }
}
