package com.siebentag.cj.game.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BowlActionFactory 
{
	@Autowired
	private ApplicationContext context;
	
	public BowlAction createEmptyBowlAction()
	{
		BowlAction action = (BowlAction)context.getBean("bowlAction");
		return action;
	}
}
