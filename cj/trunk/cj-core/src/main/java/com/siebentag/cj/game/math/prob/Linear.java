package com.siebentag.cj.game.math.prob;

public class Linear implements ProbabilityModel
{
    private double maximum;
    private double minimum;

    public Linear()
    {
    }
    
    public Linear(double minimum, double maximum)
    {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public double getMaximum()
    {
        return maximum;
    }

    public void setMaximum(double maximum)
    {
        this.maximum = maximum;
    }

    public void setMaximum(String max)
    {
    	maximum = Double.parseDouble(max);
    }
    
    public double getMinimum()
    {
        return minimum;
    }

    public void setMinimum(double minimum)
    {
        this.minimum = minimum;
    }

    public void setMinimum(String min)
    {
    	minimum = Double.parseDouble(min);
    }
    
    public double calculate(double skill)
    {
        if(skill <= 0.0)
            return minimum;
        
        if(skill >= 1.0)
            return maximum;
        
        return ((maximum - minimum) * skill) + minimum;
    }
    
    public String toString()
    {
    	return this.getClass().getSimpleName() + " (" + minimum + "->" + maximum + ")";
    }
}
