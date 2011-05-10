package com.jeff.fx.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class CreateChartAction extends AbstractAction
{
    @Autowired
    private LookForwardController controller;
    
    public CreateChartAction()
    {
        super("Chart");
        putValue(SHORT_DESCRIPTION, "Create look forward chart");
        putValue(LONG_DESCRIPTION, "Create the look forward chart based on available candle data");
    }
    
    @Override
    public void actionPerformed(ActionEvent ev)
    {
        controller.run();
    }
}
