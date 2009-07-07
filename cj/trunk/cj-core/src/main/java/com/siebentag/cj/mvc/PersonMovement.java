package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Calculator;
import com.siebentag.cj.util.math.Point3D;

public class PersonMovement
{
	private double startTime;
	private Player person;
	private Point3D source;
	private Point3D destination;
	private MoveStyle moveStyle;

	public Point3D getLocation(final double time)
	{
		double totalTime = getTotalTimeForMove();
		double relativeTime = time - startTime;

		if(totalTime > 0.0)
		{
			if(relativeTime < 0.0)
			{
				relativeTime = 0.0;
			}
			
			if(relativeTime > totalTime)
			{
				relativeTime = totalTime;
			}
			
			double percentage = relativeTime / totalTime;
			
			return Calculator.interpolate(source, destination, percentage);
		}
		else
		{
			return source;
		}
	}
	
	public double getTotalTimeForMove()
	{
		double distance = getDistance();
		double speed = getSpeed();
		
		if(speed > 0.01)
		{
			return distance / speed;
		}
		else
		{
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
	
	public double getCompletionTime()
	{
		return getStartTime() + getTotalTimeForMove();
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

	public double getStartTime()
    {
    	return startTime;
    }

	public void setStartTime(double moveStartTime)
    {
    	this.startTime = moveStartTime;
    }

	public String toString()
	{
		return String.format("%s - %s from %s => %s (time=%.1f)", person, moveStyle, source, destination, startTime);
	}	
}
