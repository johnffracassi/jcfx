package com.jeff.fx.filter;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.gui.TimeOfWeekSliderLine;
import com.jeff.fx.gui.field.PEnumComboBox;
import com.siebentag.gui.VerticalFlowLayout;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

public class FilterView extends JXPanel
{
    private TimeOfWeekSliderLine slider;
    private JCheckBox chkTimeEnabled = new JCheckBox();
    private CandlePatternFilterView patternView0;
    private CandlePatternFilterView patternView1;
    private CandlePatternFilterView patternView2;
    private JButton btnUpdate;

    public FilterView()
    {
        setLayout(new VerticalFlowLayout());
        setPreferredSize(new Dimension(350, 600));

        JXPanel pnlTime = new JXPanel();
        slider = new TimeOfWeekSliderLine("filter.times", "Time", FXDataSource.Forexite.getCalendar().getOpenTime().getMinuteOfWeek(), FXDataSource.Forexite.getCalendar().getCloseTime().getMinuteOfWeek());
        pnlTime.add(chkTimeEnabled);
        pnlTime.add(slider);
        add(pnlTime);

        patternView0 = new CandlePatternFilterView("Pattern 0");
        add(patternView0);

        patternView1 = new CandlePatternFilterView("Pattern 1");
        add(patternView1);

        patternView2 = new CandlePatternFilterView("Pattern 2");
        add(patternView2);

        JPanel pnlUpdate = new JXPanel();
        btnUpdate = new JButton("Update");
        pnlUpdate.add(btnUpdate);
        add(pnlUpdate);
    }

    public boolean isTimeEnabled()
    {
        return chkTimeEnabled.isSelected();
    }

    public TimeOfWeekSliderLine getSlider()
    {
        return slider;
    }

    public JButton getBtnUpdate()
    {
        return btnUpdate;
    }

    public CandlePattern getPattern()
    {
        return patternView0.isEnabled() ? patternView0.getValue() : null;
    }

    public CandlePattern getPreviousPattern()
    {
        return patternView1.isEnabled() ? patternView1.getValue() : null;
    }

    public CandlePattern getPrePreviousPattern()
    {
        return patternView2.isEnabled() ? patternView2.getValue() : null;
    }
}

class CandlePatternFilterView extends JXPanel
{
    private JComboBox options;
    private JComboBox offset;
    private JCheckBox enabled;

    public CandlePatternFilterView(String label)
    {
        options = new PEnumComboBox("cpfv.combo", CandlePattern.class);
        offset = new JComboBox(new Integer[] {0, -1, -2, -3});
        enabled = new JCheckBox();

        add(enabled);
        add(new JLabel(label));
        add(options);
        add(offset);
    }

    public boolean isEnabled()
    {
        return enabled.isSelected();
    }

    public int getOffset()
    {
        return (Integer)offset.getSelectedItem();
    }

    public CandlePattern getValue()
    {
        return (CandlePattern)options.getSelectedItem();
    }
}