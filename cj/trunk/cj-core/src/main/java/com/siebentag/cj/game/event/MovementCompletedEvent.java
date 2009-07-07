package com.siebentag.cj.game.event;

import com.siebentag.cj.mvc.PersonMovement;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("")
@Consumer("Umpire")
public class MovementCompletedEvent extends AbstractEvent
{
	PersonMovement movement;
	
	public MovementCompletedEvent(PersonMovement movement)
	{
		this.movement = movement;
	}
	
    public String getDescription()
    {
	    return "Movement completed - " + movement;
    }

	public PersonMovement getMovement()
    {
    	return movement;
    }
}
