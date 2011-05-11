package com.jeff.fx.filter;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.gui.TimeOfWeekSliderLine;
import com.siebentag.gui.VerticalFlowLayout;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

public class FilterView extends JXPanel
{
    private TimeOfWeekSliderLine slider;
    private JButton btnUpdate;

    public FilterView()
    {
        setLayout(new VerticalFlowLayout());
        setPreferredSize(new Dimension(280, 600));

        slider = new TimeOfWeekSliderLine("filter.time", "Time", FXDataSource.Forexite.getCalendar().getOpenTime().getMinuteOfWeek(), FXDataSource.Forexite.getCalendar().getCloseTime().getMinuteOfWeek());
        add(slider);

        btnUpdate = new JButton("Update");
        add(btnUpdate);
    }

    public TimeOfWeekSliderLine getSlider()
    {
        return slider;
    }

    public JButton getBtnUpdate()
    {
        return btnUpdate;
    }
}
