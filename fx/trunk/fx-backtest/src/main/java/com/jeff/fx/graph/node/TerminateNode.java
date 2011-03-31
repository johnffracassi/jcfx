package com.jeff.fx.graph.node;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.lookforward.CandleFilterModel;

public class TerminateNode extends BaseNode
{
    public TerminateNode()
    {
        super("Terminate");
    }

    @Override
    public boolean evaluate(CandleDataPoint candle, CandleFilterModel model) {
        return false;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
