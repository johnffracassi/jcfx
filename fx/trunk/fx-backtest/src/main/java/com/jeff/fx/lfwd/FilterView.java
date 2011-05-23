package com.jeff.fx.lfwd;

import com.jeff.fx.backtest.DatasetDefinitionPanel;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.gui.TimeOfWeekSliderLine;
import com.jeff.fx.gui.field.PEnumComboBox;
import com.jeff.fx.lfwd.candlepattern.CandlePattern;
import com.jeff.fx.lfwd.candletype.CandleType;
import com.siebentag.gui.VerticalFlowLayout;
import org.jdesktop.swingx.JXPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@org.springframework.stereotype.Component
public class FilterView extends JXPanel
{
    private TimeOfWeekSliderLine slider;
    private JCheckBox chkTimeEnabled = new JCheckBox();
    private CandleTypeFilterView typeView[];
    private CandlePatternFilterView patternView;
    private ExpressionFilterView expressionView;
    private JButton btnUpdate;

    @Autowired
    private DatasetDefinitionPanel pnlDataset;

    public FilterView()
    {
        setLayout(new VerticalFlowLayout());
        setPreferredSize(new Dimension(325, 600));
    }

    @PostConstruct
    private void init()
    {
        add(pnlDataset);

        JXPanel pnlTime = new JXPanel();
        slider = new TimeOfWeekSliderLine("lfwd.times", "Time", FXDataSource.Forexite.getCalendar().getOpenTime().getMinuteOfWeek(), FXDataSource.Forexite.getCalendar().getCloseTime().getMinuteOfWeek());
        pnlTime.add(chkTimeEnabled);
        pnlTime.add(slider);
        add(pnlTime);

        expressionView = new ExpressionFilterView();
        add(expressionView);

        patternView = new CandlePatternFilterView("Pattern");
        add(patternView);

        typeView = new CandleTypeFilterView[5];
        for(int i=0; i< typeView.length; i++)
        {
            typeView[i] = new CandleTypeFilterView("Candle Type " + i, i);
            add(typeView[i]);
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

    public CandlePattern getPattern()
    {
        return patternView.isEnabled() ? patternView.getValue() : null;
    }

    public CandleType getType(int idx)
    {
        return typeView[idx].isEnabled() ? typeView[idx].getValue() : null;
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
    private JCheckBox enabled;
    private JComboBox options;

    public CandlePatternFilterView(String label)
    {
        options = new PEnumComboBox("cpfv.combo", CandlePattern.class);

        enabled = new JCheckBox();

        add(enabled);
        add(new JLabel(label));
        add(options);
    }

    public boolean isEnabled()
    {
        return enabled.isSelected();
    }

    public CandlePattern getValue()
    {
        return (CandlePattern)options.getSelectedItem();
    }
}

class CandleTypeFilterView extends JXPanel
{
    private JComboBox options;
    private JComboBox offset;
    private JCheckBox enabled;

    public CandleTypeFilterView(String label, int offset)
    {
        options = new PEnumComboBox("ctfv.combo."+offset, CandleType.class);
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

    public CandleType getValue()
    {
        return (CandleType)options.getSelectedItem();
    }
}