package com.jeff.fx.lfwd.candlepattern;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.candlepattern.def.*;

public enum CandlePattern
{
    BearishEveningDojiStar(new BearishEveningDojiStar()),
    BullishMorningDojiStar(new BullishMorningDojiStar()),
    Pennant(new Pennant());

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
