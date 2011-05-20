package com.jeff.fx.graph.node;

import com.jeff.fx.lfwd.CandleFilterModel;

public class ProceedNode extends BaseNode
{
    public ProceedNode()
    {
        super("Proceed");
    }

    @Override
    public boolean evaluate(CandleFilterModel model, int idx) {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
