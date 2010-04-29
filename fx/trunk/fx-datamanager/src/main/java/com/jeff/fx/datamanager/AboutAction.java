package com.jeff.fx.datamanager;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AboutAction extends AbstractAction 
{
	public AboutAction()
	{
		putValue(SHORT_DESCRIPTION, "About the game");
		putValue(LONG_DESCRIPTION, "About the game");
		putValue(NAME, "About...");
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		JOptionPane.showMessageDialog(null, "World Greatest Cricket Game v0.01\n(c)2008 Jeffrey Cann");
	}
}
