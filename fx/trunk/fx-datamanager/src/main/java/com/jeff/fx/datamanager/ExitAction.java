package com.jeff.fx.datamanager;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ExitAction extends AbstractAction 
{
	public ExitAction()
	{
		putValue(SHORT_DESCRIPTION, "Quit the application");
		putValue(LONG_DESCRIPTION, "Quit the application");
		putValue(NAME, "Exit");
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		int option = JOptionPane.showConfirmDialog(null, "Do you really want to quit?");
		
		if(option == JOptionPane.OK_OPTION)
		{
			System.exit(0);
		}
	}
}
