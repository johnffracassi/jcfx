package com.jeff.fx.lfwd.candlepattern;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.candlepattern.def.BearishEveningDojiStar;
import com.jeff.fx.lfwd.candlepattern.def.BullishMorningDojiStar;
import com.jeff.fx.lfwd.candlepattern.def.ReversePennant;
import com.jeff.fx.lfwd.candlepattern.def.TestPattern;

public enum CandlePattern
{
    TestPattern(new TestPattern()),
    BearishEveningDojiStar(new BearishEveningDojiStar()),
    BullishMorningDojiStar(new BullishMorningDojiStar()),
    ReversePennant(new ReversePennant());

    private CandlePatternDef pattern;

    CandlePattern(CandlePatternDef pattern)
    {
        this.pattern = pattern;
    }

    public boolean is(CandleFilterModel model)
    {
        return pattern.is(model);
    }
}
