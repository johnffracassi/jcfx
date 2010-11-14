package com.jeff.fx.rules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeOfWeekRangeController
{
    private TimeOfWeekRangeView view;
    
    public TimeOfWeekRangeController()
    {
        view = new TimeOfWeekRangeView();
    }
    
    public TimeOfWeekRangeView getView()
    {
        return view;
    }
}
