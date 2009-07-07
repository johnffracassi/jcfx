package com.siebentag.cj.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.model.Team;
import com.siebentag.cj.mvc.Controller;
import com.siebentag.cj.mvc.TeamLoader;

@SuppressWarnings("serial")
public class NewMatchAction extends AbstractAction 
{
	@Autowired
	private TeamLoader teamLoader;
	
	@Autowired
	private Controller controller;
	
	public NewMatchAction()
	{
		putValue(SHORT_DESCRIPTION, "New Game");
		putValue(LONG_DESCRIPTION, "Start a new match");
		putValue(NAME, "New Game...");
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		Team aus = teamLoader.loadTeam("aus");
		Team eng = teamLoader.loadTeam("eng");
		
		controller.resetForInnings(aus, eng);
	}
}
