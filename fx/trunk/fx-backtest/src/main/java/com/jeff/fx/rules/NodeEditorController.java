package com.jeff.fx.rules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NodeEditorController
{
    private NodeEditorView view = new NodeEditorView();
    
    public NodeEditorController()
    {
        view.getCboType().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typeChanged();
            }
        });
        
        view.getCboSubType().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subTypeChanged();
            }
        });
    }
    
    private void typeChanged()
    {
        String selectedType = (String)view.getCboType().getSelectedItem();
        System.out.println("selected " + selectedType);
    }
    
    private void subTypeChanged() 
    {
        
    }
}
