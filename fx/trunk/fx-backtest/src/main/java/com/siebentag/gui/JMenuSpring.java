package com.siebentag.gui;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;

@SuppressWarnings("serial") 
public class JMenuSpring extends JMenu
{
	public void setActions(List<Action> actions)
	{
		for(Action action : actions)
		{
			if(action != null)
			{
				add(action);
			}
			else
			{
				addSeparator();
			}
		}
	}
	
	public List<JMenu> getActions()
	{
		throw new UnsupportedOperationException();
	}

}
