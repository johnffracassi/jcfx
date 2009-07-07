package com.siebentag.cj.game.action;

import com.siebentag.cj.mvc.PersonController;
import com.siebentag.cj.mvc.PersonMovement;
import com.siebentag.cj.queue.Scope;

public class MovementAction extends AbstractAction 
{
	private PersonController controller;
	private PersonMovement movement;
	
	protected MovementAction()
	{
	}
	
	public void run() 
	{
		// tell the relevant controller to move the player
		controller.doMove(movement);
		
		// TODO log the event for when the person is expected to stop moving
	}

	@Override public void cancel() 
	{
		super.cancel();

		// remove the completion event

		// stop the person where he is
	}
	
	@Override public Scope getScope() 
	{
		return Scope.Ball;
	}

	public PersonController getController()
    {
    	return controller;
    }

	public void setController(PersonController controller)
    {
    	this.controller = controller;
    }

	public PersonMovement getMovement()
    {
    	return movement;
    }

	public void setMovement(PersonMovement movement)
    {
    	this.movement = movement;
    }
}
