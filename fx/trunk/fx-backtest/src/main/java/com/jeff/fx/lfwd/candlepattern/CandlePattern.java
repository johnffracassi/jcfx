package com.jeff.fx.lfwd.candlepattern;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.candlepattern.def.BearishEveningDojiStar;
import com.jeff.fx.lfwd.candlepattern.def.BullishMorningDojiStar;

public enum CandlePattern
{
    BearishEveningDojiStar(new BearishEveningDojiStar()),
    BullishMorningDojiStar(new BullishMorningDojiStar());

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
