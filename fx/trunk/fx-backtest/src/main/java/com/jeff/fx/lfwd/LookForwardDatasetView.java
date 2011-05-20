package com.jeff.fx.lfwd;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;

public class LookForwardDatasetView extends JXPanel {

    private JXTable table;

    public LookForwardDatasetView()
    {
        setLayout(new BorderLayout());

        table = new JXTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public JXTable getTable()
    {
        return table;
    }
}
