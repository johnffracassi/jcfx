package com.jeff.fx.rules;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

import com.jeff.fx.common.TimeOfWeek;

public class TimeOfWeekEditorView extends JPanel
{
    private JSpinner spinDayOfWeek;
    private JSpinner spinHour;
    private JSpinner spinMinute;

    /**
     * Create the panel.
     */
    public TimeOfWeekEditorView()
    {
        setLayout(new MigLayout("", "[][][]", "[]"));

        spinDayOfWeek = new JSpinner();
        spinDayOfWeek.setModel(new SpinnerListModel(TimeOfWeek.FULL_DAY));

        add(spinDayOfWeek, "cell 0 0,growx");

        spinHour = new JSpinner();
        spinHour.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        add(spinHour, "cell 1 0,growx");

        spinMinute = new JSpinner();
        spinMinute.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        add(spinMinute, "cell 2 0,growx");
    }

    public JSpinner getSpinDayOfWeek()
    {
        return spinDayOfWeek;
    }

    public JSpinner getSpinHour()
    {
        return spinHour;
    }

    public JSpinner getSpinMinute()
    {
        return spinMinute;
    }
}
