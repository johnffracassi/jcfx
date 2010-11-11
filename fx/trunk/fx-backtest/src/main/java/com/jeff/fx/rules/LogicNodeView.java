package com.jeff.fx.rules;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class LogicNodeView extends JPanel
{
    public LogicNodeView()
    {
        setLayout(new MigLayout("", "[][][grow]", "[][]"));
        
        JLabel lblNodeClass = new JLabel("Node Class");
        add(lblNodeClass, "cell 0 0");
        
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"And", "Or", "Xor", "Nand"}));
        add(comboBox, "cell 2 0,growx");

    }
}
