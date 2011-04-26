package com.jeff.fx.graph.node;

import com.jeff.fx.filter.CandleFilterModel;

public class TerminateNode extends BaseNode
{
    public TerminateNode()
    {
        super("Terminate");
    }

    @Override
    public boolean evaluate(CandleFilterModel model, int idx) {
        return false;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
