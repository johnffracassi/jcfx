package com.siebentag.fx.mv;

import java.awt.event.ActionEvent;

import org.jdesktop.swingx.action.AbstractActionExt;
import org.springframework.stereotype.Component;

@Component
public class ExitAction extends AbstractActionExt
{
	private static final long serialVersionUID = -1663180245517709452L;

	public ExitAction()
	{
		setName("Exit");
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		System.exit(0);
	}
}
