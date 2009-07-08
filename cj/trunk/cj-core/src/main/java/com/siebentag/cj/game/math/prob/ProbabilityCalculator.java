package com.siebentag.cj.game.math.prob;

public class ProbabilityCalculator
{
    public static boolean determineOutcome(ProbabilityModel model, double skillLevel)
    {
        if(skillLevel > 1.0)
            skillLevel = 1.0;
        
        if(skillLevel < 0.0)
            skillLevel = 0.0;
        
        double probability = model.calculate(skillLevel);
        double random = Math.random();
        
        return (random > probability);
    }
}
