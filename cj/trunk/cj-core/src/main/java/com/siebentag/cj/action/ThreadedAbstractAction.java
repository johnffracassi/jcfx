package com.siebentag.cj.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public abstract class ThreadedAbstractAction extends AbstractAction
{
    private static final long serialVersionUID = 6121166928226989072L;

	public abstract void performAction();
	
	public final void actionPerformed(ActionEvent e) 
	{
		new Thread(new Runnable() 
		{
			public void run() {
				performAction();
			}
		}).start();
	}
}
