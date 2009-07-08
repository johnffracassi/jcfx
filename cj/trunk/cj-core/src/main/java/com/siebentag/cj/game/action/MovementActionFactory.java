package com.siebentag.cj.game.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.mvc.Controller;
import com.siebentag.cj.mvc.MoveStyle;
import com.siebentag.cj.mvc.PersonController;
import com.siebentag.cj.mvc.PersonMovement;
import com.siebentag.cj.queue.Priority;
import com.siebentag.cj.util.math.Point3D;

@Component
public class MovementActionFactory
{
	@Autowired
	Controller controller;
	
	private MovementAction createMovementAction(PersonRole personRole, Player player, Point3D source, Point3D destination, double time, MoveStyle style)
	{
		MovementAction action = new MovementAction();
		PersonMovement movement = new PersonMovement();
		
		PersonController pc = controller.getControllerByRole(personRole);
		action.setController(pc);
		action.setState(ActionState.Idle);
		action.setPriority(Priority.Low);
		action.setTime(time);
		
		movement.setMoveStyle(style);
		movement.setPerson(player);
		movement.setStartTime(time);
		movement.setSource(source);
		movement.setDestination(destination);
		
		action.setMovement(movement);
		
		return action;
	}
	
	public MovementAction createRunToAction(PersonRole personRole, Player player, Point3D source, Point3D destination, double time)
	{
		return createMovementAction(personRole, player, source, destination, time, MoveStyle.Run);
	}
	
	public MovementAction createWalkToAction(PersonRole personRole, Player player, Point3D source, Point3D destination, double time)
	{
		return createMovementAction(personRole, player, source, destination, time, MoveStyle.Walk);
	}
}
