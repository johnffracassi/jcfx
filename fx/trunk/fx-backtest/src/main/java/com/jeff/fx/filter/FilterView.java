package com.jeff.fx.filter;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.gui.TimeOfWeekSliderLine;
import com.jeff.fx.gui.field.PComboBox;
import com.jeff.fx.gui.field.PEnumComboBox;
import com.siebentag.gui.VerticalFlowLayout;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

public class FilterView extends JXPanel
{
    private TimeOfWeekSliderLine slider;
    private CandlePatternFilterView patternView;
    private JButton btnUpdate;
    private JTextField txtExpression;

    public FilterView()
    {
        setLayout(new VerticalFlowLayout());
        setPreferredSize(new Dimension(300, 600));

        slider = new TimeOfWeekSliderLine("filter.times", "Time", FXDataSource.Forexite.getCalendar().getOpenTime().getMinuteOfWeek(), FXDataSource.Forexite.getCalendar().getCloseTime().getMinuteOfWeek());
        add(slider);

        txtExpression = new JTextField();
        add(txtExpression);

        patternView = new CandlePatternFilterView("Pattern");
        add(patternView);

        JPanel pnlUpdate = new JXPanel();
        btnUpdate = new JButton("Update");
        pnlUpdate.add(btnUpdate);
        add(pnlUpdate);
    }

    public TimeOfWeekSliderLine getSlider()
    {
        return slider;
    }

    public JButton getBtnUpdate()
    {
        return btnUpdate;
    }

    public String getExpression()
    {
        return txtExpression.getText();
    }

    public CandlePattern getPattern()
    {
        return patternView.getValue();
    }
}

class CandlePatternFilterView extends JXPanel
{
    private JComboBox options;

    public CandlePatternFilterView(String label)
    {
        add(new JLabel(label));
        options = new PEnumComboBox("cpfv.combo", CandlePattern.class);
        add(options);
    }

    public CandlePattern getValue()
    {
        return (CandlePattern)options.getSelectedItem();
    }
}