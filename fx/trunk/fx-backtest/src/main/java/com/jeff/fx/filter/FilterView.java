package com.jeff.fx.filter;

import com.jeff.fx.backtest.DatasetDefinitionPanel;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.filter.candletype.CandlePattern;
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
    private CandlePatternFilterView patternView[];
    private ExpressionFilterView expressionView;
    private JButton btnUpdate;
    private DatasetDefinitionPanel pnlDataset;

    public FilterView()
    {
        setLayout(new VerticalFlowLayout());
        setPreferredSize(new Dimension(325, 600));

        pnlDataset = new DatasetDefinitionPanel("lfwd");
        add(pnlDataset);

        JXPanel pnlTime = new JXPanel();
        slider = new TimeOfWeekSliderLine("filter.times", "Time", FXDataSource.Forexite.getCalendar().getOpenTime().getMinuteOfWeek(), FXDataSource.Forexite.getCalendar().getCloseTime().getMinuteOfWeek());
        pnlTime.add(chkTimeEnabled);
        pnlTime.add(slider);
        add(pnlTime);

        expressionView = new ExpressionFilterView();
        add(expressionView);

        patternView = new CandlePatternFilterView[5];
        for(int i=0; i<patternView.length; i++)
        {
            patternView[i] = new CandlePatternFilterView("Pattern " + i, i);
            add(patternView[i]);
        }

        JPanel pnlUpdate = new JXPanel();
        btnUpdate = new JButton("Update");
        pnlUpdate.add(btnUpdate);
        add(pnlUpdate);
    }

    public DatasetDefinitionPanel getDatasetDefinitionPanel()
    {
        return pnlDataset;
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

    public String getExpression()
    {
        return (expressionView.isEnabled() ? expressionView.getExpression() : null);
    }

    public CandlePattern getPattern(int idx)
    {
        return patternView[idx].isEnabled() ? patternView[idx].getValue() : null;
    }
}

class ExpressionFilterView extends JXPanel
{
    private JCheckBox enabled;
    private JTextField expression;

    public ExpressionFilterView()
    {
        setLayout(new BorderLayout());

        enabled = new JCheckBox();
        add(enabled, BorderLayout.WEST);

        expression = new JTextField();
        add(expression, BorderLayout.CENTER);
    }

    public String getExpression()
    {
        return enabled.isSelected() ? expression.getText() : null;
    }
}

class CandlePatternFilterView extends JXPanel
{
    private JComboBox options;
    private JComboBox offset;
    private JCheckBox enabled;

    public CandlePatternFilterView(String label, int offset)
    {
        options = new PEnumComboBox("cpfv.combo."+offset, CandlePattern.class);
        this.offset = new JComboBox(new Integer[] {0, -1, -2, -3, -4});
        this.offset.setSelectedItem(-offset);

        enabled = new JCheckBox();

        add(enabled);
        add(new JLabel(label));
        add(options);
        add(this.offset);
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