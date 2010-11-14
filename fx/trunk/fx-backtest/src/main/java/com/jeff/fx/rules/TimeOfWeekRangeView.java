package com.jeff.fx.rules;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class TimeOfWeekRangeView extends JPanel
{
    private TimeOfWeekEditorView towFrom;
    private TimeOfWeekEditorView towTo;
    private JCheckBox chkInclusiveTo;
    private JCheckBox chkInclusiveFrom;
    public TimeOfWeekRangeView()
    {
        setLayout(new MigLayout("", "[75][grow][]", "[grow][grow]"));
        
        JLabel lblFrom = new JLabel("From");
        add(lblFrom, "cell 0 0");
        
        towFrom = new TimeOfWeekEditorView();
        add(towFrom, "cell 1 0,alignx left,growy");
        
        chkInclusiveFrom = new JCheckBox("Inclusive");
        add(chkInclusiveFrom, "cell 2 0");
        
        JLabel lblTo = new JLabel("To");
        add(lblTo, "cell 0 1");
        
        towTo = new TimeOfWeekEditorView();
        add(towTo, "flowx,cell 1 1,alignx left,aligny top");
        
        chkInclusiveTo = new JCheckBox("Inclusive");
        add(chkInclusiveTo, "cell 2 1");
        
    }
    public TimeOfWeekEditorView getTowFrom() {
        return towFrom;
    }
    public TimeOfWeekEditorView getTowTo() {
        return towTo;
    }
    public JCheckBox getChkInclusiveTo() {
        return chkInclusiveTo;
    }
    public JCheckBox getChkInclusiveFrom() {
        return chkInclusiveFrom;
    }
}
