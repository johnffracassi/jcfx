package com.jeff.fx.rules;

import org.junit.Assert;
import org.junit.Test;

public class OperandTest
{
    @Test
    public void simpleOperandTest()
    {
        Assert.assertTrue(Operand.gt.evaluate(5, 1));
        Assert.assertTrue(Operand.gt.evaluate(5.05, 1.03));
        Assert.assertTrue(Operand.gt.evaluate(4.96, 1));
        Assert.assertTrue(Operand.gt.evaluate(5f, 1.0));

        Assert.assertFalse(Operand.gt.evaluate(1, 1));
        Assert.assertFalse(Operand.gt.evaluate(1f, 1));
        Assert.assertFalse(Operand.gt.evaluate(1f, 1.0));

        Assert.assertTrue(Operand.eq.evaluate(1, 1));
        Assert.assertTrue(Operand.eq.evaluate(1f, 1));
        Assert.assertTrue(Operand.eq.evaluate(1f, 1.0));

        
        Assert.assertFalse(Operand.gt.evaluate(1, 5));
        Assert.assertFalse(Operand.gt.evaluate(5.05, 11.03));
        Assert.assertFalse(Operand.gt.evaluate(4.96, 11));
        Assert.assertFalse(Operand.gt.evaluate(5f, 11.0));
    }
}
