package com.jeff.fx.rules;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;

public class NonContiguousCandleView extends JPanel
{
    private JTable table;
    private JLabel lblStatus;
    private JButton btnApply;

    /**
     * Create the panel.
     */
    public NonContiguousCandleView()
    {
        setLayout(new BorderLayout(0, 0));
        
        JToolBar toolBar = new JToolBar();
        add(toolBar, BorderLayout.NORTH);
        
        btnApply = new JButton("Apply");
        toolBar.add(btnApply);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        lblStatus = new JLabel("New label");
        panel.add(lblStatus);
        
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        
        table = new JTable();
        table.setFillsViewportHeight(true);
        scrollPane.setViewportView(table);

    }

    public JTable getTable() {
        return table;
    }
    public JLabel getLblStatus() {
        return lblStatus;
    }
    public JButton getBtnApply() {
        return btnApply;
    }
}
