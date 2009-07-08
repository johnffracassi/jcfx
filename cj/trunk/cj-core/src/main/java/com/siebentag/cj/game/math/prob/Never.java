package com.siebentag.cj.game.math.prob;

public class Never implements ProbabilityModel
{
    public double calculate(double skill)
    {
        return 0.01;
    }

    public String toString()
    {
    	return this.getClass().getSimpleName();
    }
}
