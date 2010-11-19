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
        if(o1 instanceof Number && o2 instanceof Number)
        {
            int result = compareNumbers((Number)o1, (Number)o2);
            return comparatorValues[result + 1];
        }
        else
        {
            int result = o1.compareTo(o2);
            return comparatorValues[result + 1];
        }
    }
    
    private int compareNumbers(Number n1, Number n2)
    {
        double d1 = n1.doubleValue();
        double d2 = n2.doubleValue();
        
        if(d1 < d2)
            return -1;
        if(d1 > d2)
            return 1;
        return 0;
    }
    
    public String getLabel()
    {
        return label;
    }
}
