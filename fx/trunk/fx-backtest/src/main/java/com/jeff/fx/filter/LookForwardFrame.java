package com.jeff.fx.filter;

import com.jeff.fx.action.ExitAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class LookForwardFrame extends JFrame
{
    @Autowired
    private LookForwardChartController chartController;

    @Autowired
    private LookForwardDatasetController datasetController;

    @Autowired
    private FilterController filterController;

    @Autowired
    private ExitAction exitAction;
    
    public LookForwardFrame() 
    {
    }
    
    @PostConstruct
    private void init()
    {
        setSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.add(datasetController.getView(), "Data Points");
        tabbedPane.add(chartController.getView(), "Chart");

        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(exitAction);

        getContentPane().add(filterController.getView(), BorderLayout.WEST);
    }
}
