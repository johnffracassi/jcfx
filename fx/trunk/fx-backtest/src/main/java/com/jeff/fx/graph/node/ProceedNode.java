package com.jeff.fx.graph.node;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lookforward.CandleFilterModel;

public class ProceedNode extends BaseNode
{
    public ProceedNode()
    {
        super("Process");
    }

    @Override
    public boolean evaluate(CandleDataPoint candle, CandleFilterModel model) {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
