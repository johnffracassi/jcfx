package com.jeff.fx.lfwd;

import javax.swing.*;
import java.awt.*;

public class LookForwardView extends JPanel
{
    private JTable tblResults;
    private JTable tblCandles;
    private JTable tblConditions;
    public LookForwardView()
    {
        setLayout(new BorderLayout());
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.22);
        add(splitPane, BorderLayout.CENTER);
        
        JSplitPane splitResults = new JSplitPane();
        splitResults.setResizeWeight(0.4);
        splitResults.setOrientation(JSplitPane.VERTICAL_SPLIT);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Results", splitResults);
        splitPane.setRightComponent(tabbedPane);
        
        JPanel pnlCandles = new JPanel();
        pnlCandles.setBackground(new Color(255, 222, 173));
        splitResults.setLeftComponent(pnlCandles);
        pnlCandles.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollCandles = new JScrollPane();
        pnlCandles.add(scrollCandles);
        
        tblCandles = new JTable();
        scrollCandles.setViewportView(tblCandles);
        
        JPanel pnlResults = new JPanel();
        pnlResults.setBackground(Color.PINK);
        splitResults.setRightComponent(pnlResults);
        pnlResults.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollResults = new JScrollPane();
        pnlResults.add(scrollResults, BorderLayout.CENTER);
        
        tblResults = new JTable();
        tblResults.setShowVerticalLines(true);
        tblResults.setShowHorizontalLines(true);
        scrollResults.setViewportView(tblResults);
        
        JPanel pnlChart = new JPanel();
        tabbedPane.addTab("Chart", null, pnlChart, null);
        
        JPanel pnlConditions = new JPanel();
        pnlConditions.setBackground(new Color(127, 255, 212));
        splitPane.setLeftComponent(pnlConditions);
        pnlConditions.setLayout(new BorderLayout(0, 0));
        
        JToolBar toolBar = new JToolBar();
        pnlConditions.add(toolBar, BorderLayout.NORTH);
        
        JButton btnAddCondition = new JButton("");
        btnAddCondition.setIcon(new ImageIcon(LookForwardView.class.getResource("/images/add.png")));
        toolBar.add(btnAddCondition);
        
        JScrollPane scrollConditions = new JScrollPane();
        pnlConditions.add(scrollConditions, BorderLayout.CENTER);
        
        tblConditions = new JTable();
        scrollConditions.setViewportView(tblConditions);
        
    }
}
