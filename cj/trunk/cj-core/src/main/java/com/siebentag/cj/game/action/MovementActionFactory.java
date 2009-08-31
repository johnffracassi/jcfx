package com.siebentag.cj.game.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.mvc.Controller;
import com.siebentag.cj.mvc.MoveStyle;
import com.siebentag.cj.mvc.PersonController;
import com.siebentag.cj.mvc.PersonMovement;
import com.siebentag.cj.queue.Priority;
import com.siebentag.cj.queue.Scope;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

@Component
public class MovementActionFactory
{
	@Autowired
	Controller controller;
	
	private MovementAction createMovementAction(PersonRole personRole, Player player, Point3D source, Point3D destination, Time time, MoveStyle style)
	{
		PersonMovement movement = new PersonMovement();
		movement.setMoveStyle(style);
		movement.setPerson(player);
		movement.setStartTime(time);
		movement.setSource(source);
		movement.setDestination(destination);
		
		MovementAction action = new MovementAction(controller.getControllerByRole(personRole), movement);
		action.setState(ActionState.Idle);
		action.setPriority(Priority.Low);
		action.setTime(time);		
		
		return action;
	}
	
	public MovementAction createRunToAction(PersonRole personRole, Player player, Point3D source, Point3D destination, Time time)
	{
		return createMovementAction(personRole, player, source, destination, time, MoveStyle.Run);
	}
	
	public MovementAction createWalkToAction(PersonRole personRole, Player player, Point3D source, Point3D destination, Time time)
	{
		return createMovementAction(personRole, player, source, destination, time, MoveStyle.Walk);
	}
	
	public class MovementAction extends AbstractAction 
	{
		private PersonController controller;
		private PersonMovement movement;
		
		private MovementAction(PersonController controller, PersonMovement movement)
		{
			this.controller = controller;
			this.movement = movement;
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
}
