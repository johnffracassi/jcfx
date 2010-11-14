package com.jeff.fx.rules;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jeff.fx.common.TimeOfWeek;

public class TimeOfWeekEditorController
{
    private TimeOfWeekEditorView view;
    private ValueChangeListener listener;
    
    public TimeOfWeekEditorController()
    {
        view = new TimeOfWeekEditorView();
        
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                if(listener != null)
                {
                    listener.valueChanged(TimeOfWeekEditorController.this);
                }
            }
        };
        
        view.getSpinDayOfWeek().addChangeListener(changeListener);
        view.getSpinHour().addChangeListener(changeListener);
        view.getSpinMinute().addChangeListener(changeListener);
    }
    
    public void setListener(ValueChangeListener listener)
    {
        this.listener = listener;
    }
    
    public TimeOfWeek getTimeOfWeek()
    {
        int dayOfWeek = TimeOfWeek.toDayOfWeek((String)(view.getSpinDayOfWeek().getValue()));
        int hour = (Integer)view.getSpinHour().getValue();
        int minute = (Integer)view.getSpinMinute().getValue();
        
        return new TimeOfWeek(dayOfWeek, hour, minute);
    }
    
    public void setTimeOfWeek(TimeOfWeek time)
    {
        view.getSpinDayOfWeek().setValue(TimeOfWeek.FULL_DAY[time.getDayOfWeek()]);
        view.getSpinHour().setValue(time.getTime().getHourOfDay());
        view.getSpinMinute().setValue(time.getTime().getMinuteOfHour());
    }
    
    public TimeOfWeekEditorView getView()
    {
        return view;
    }
}
