package com.jeff.fx.lfwd;

import com.jeff.fx.rules.Operand;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

public class ConditionLineView extends JXPanel
{
    private JTextField textField;
    public ConditionLineView()
    {
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setVgap(3);
        flowLayout.setHgap(3);
        flowLayout.setAlignment(FlowLayout.LEFT);
        
        JButton btnCandledatetime = new JButton("candle.dateTime");
        add(btnCandledatetime);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(Operand.values()));
        add(comboBox);
        
        textField = new JTextField();
        add(textField);
        textField.setColumns(10);
    }
}
