package com.jeff.fx.filter;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandleFilterModelEvaluatorTest
{
    private CandleFilterModelEvaluator evaluator;

    @Before
    public void setup()
    {
        evaluator = new CandleFilterModelEvaluator();
    }

    @Test
    public void testSimpleIndicatorShorthandResolves()
    {
        assertThat(evaluator.resolveShorthand("#sma(14)"), is("ind['sma(14)'][0]"));
    }

    @Test
    public void testSimpleIndicatorWithIndexShorthandResolves()
    {
        assertThat(evaluator.resolveShorthand("#sma(14)[3]"), is("ind['sma(14)'][3]"));
    }

    @Test
    public void testSimpleIndicatorShorthandAndExprResolves()
    {
        assertThat(evaluator.resolveShorthand("#sma(14)[4] + 0.0007"), is("ind['sma(14)'][4] + 0.0007"));
    }

    @Test
    public void testSimpleIndicatorShorthandAndMultipleExprResolves()
    {
        assertThat(evaluator.resolveShorthand("0.0005 + #sma(14)[5] + 0.0007"), is("0.0005 + ind['sma(14)'][5] + 0.0007"));
    }
}
