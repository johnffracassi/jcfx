package com.jeff.fx.rules;


public enum Operand
{
    eq("=", new boolean[] { false, true, false }),
    ne("!=", new boolean[] { true, false, true }), 
    gt(">", new boolean[] { false, false, true }),
    ge(">=", new boolean[] { false, true, true }),
    lt("<", new boolean[] { true, false, false }), 
    le("<=", new boolean[] { true, true, false });

    private boolean[] comparatorValues;
    private String label;
    
    private Operand(String label, boolean[] comparatorValues)
    {
        this.comparatorValues = comparatorValues;
        this.label = label;
    }
    
    public boolean evaluate(Comparable o1, Comparable o2)
    {
        int result = o1.compareTo(o2);
        return comparatorValues[result + 1];
    }
    
    public String getLabel()
    {
        return label;
    }
}
