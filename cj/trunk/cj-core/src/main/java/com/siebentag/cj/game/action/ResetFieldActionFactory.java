package com.siebentag.cj.game.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.mvc.FielderController;

@Component
public class ResetFieldActionFactory 
{
	@Autowired
	private FielderController fielderController;
	
	public ResetFieldAction createEmptyBowlAction()
	{
		return new ResetFieldAction();
	}
	
	class ResetFieldAction extends AbstractAction 
	{
		@Override
		public void run() 
		{
			fielderController.resetForBall();
		}
	}
}
