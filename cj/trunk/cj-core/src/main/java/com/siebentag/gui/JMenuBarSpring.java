package com.siebentag.gui;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial") 
public class JMenuBarSpring extends JMenuBar
{
	public void setMenus(List<JMenu> menus)
	{
		removeAll();
		
		for(JMenu menu : menus)
		{
			add(menu);
		}
	}
	
	public List<JMenu> getMenus()
	{
		throw new UnsupportedOperationException();
	}
}
