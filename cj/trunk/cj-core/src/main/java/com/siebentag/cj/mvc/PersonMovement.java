package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Calculator;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

public class PersonMovement
{
	private Time startTime;
	private Player person;
	private Point3D source;
	private Point3D destination;
	private MoveStyle moveStyle;

	public Point3D getLocation(final Time time)
	{
		// the number of seconds for the move
		double totalTime = getTotalTimeForMove();
		
		// the number of seconds into the move
		double moveTime = time.getTime() - startTime.getTime();

		// is there a move, or are we just standing still?
		if(totalTime > 0.0)	{
			
			// are we before the move start?
			if(moveTime < 0.0) {
				moveTime = 0.0;
			}
			
			// are we at the end of the move?
			if(moveTime > totalTime) {
				moveTime = totalTime;
			}
			
			// how far through the move are we?
			double percentage = moveTime / totalTime;
			
			// interpolate the move for an exact figure
			return Calculator.interpolate(source, destination, percentage);
		}
		else {
			return source;
		}
	}
	
	public double getTotalTimeForMove() {
		
		double distance = getDistance();
		double speed = getSpeed();
		
		if(speed > 0.01) {
			return distance / speed;
		} else {
			return 0;
		}		
	}
	
	public double getDistance()
	{
		return Calculator.distance(source, destination);
	}
	
	public double getSpeed()
	{
		double moveSpeed = 0.0;
		
		if(moveStyle == MoveStyle.Run)
		{
			moveSpeed = 7.5;
		}
		else if(moveStyle == MoveStyle.Walk)
		{
			moveSpeed = 2.5;
		}
		else if(moveStyle == MoveStyle.None)
		{
			moveSpeed = 0.0;
		}
		
		return moveSpeed;
	}
	
	public Time getCompletionTime()
	{
		return getStartTime().add(getTotalTimeForMove());
	}
	
	public Player getPerson()
	{
		return person;
	}

	public void setPerson(Player player)
	{
		this.person = player;
	}

	public Point3D getSource()
	{
		return source;
	}

	public void setSource(Point3D source)
	{
		this.source = source.floored();
	}

	public Point3D getDestination()
	{
		return destination;
	}

	public void setDestination(Point3D destination)
	{
		this.destination = destination.floored();
	}

	public MoveStyle getMoveStyle()
    {
    	return moveStyle;
    }

	public void setMoveStyle(MoveStyle moveStyle)
    {
    	this.moveStyle = moveStyle;
    }

	public Time getStartTime()
    {
    	return startTime;
    }

	public void setStartTime(Time moveStartTime)
    {
    	this.startTime = moveStartTime;
    }

	public String toString()
	{
		return String.format("%s - %s from %s => %s (time=%.1f)", person, moveStyle, source, destination, startTime);
	}	
}
