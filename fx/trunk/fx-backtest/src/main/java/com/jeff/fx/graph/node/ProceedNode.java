package com.jeff.fx.graph.node;

import com.jeff.fx.filter.CandleFilterModel;

public class ProceedNode extends BaseNode
{
    public ProceedNode()
    {
        super("Process");
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
