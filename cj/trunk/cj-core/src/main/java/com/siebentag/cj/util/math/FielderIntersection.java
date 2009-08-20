package com.siebentag.cj.util.math;

import com.siebentag.cj.model.Player;


public class FielderIntersection
{
	private TrajectoryPoint location;
	private Player player;
	private Time ballTime;
	private double playerTime;

	public FielderIntersection()
	{
		location = null;
		player = null;
		ballTime = null;
		playerTime = 0.0;
	}
	
	public TrajectoryPoint getLocation()
	{
		return location;
	}

	public void setLocation(TrajectoryPoint loc)
	{
		this.location = loc;
	}

	public Player getPlayer()
    {
    	return player;
    }

	public void setPlayer(Player player)
    {
    	this.player = player;
    }

	public Time getBallTime()
    {
    	return ballTime;
    }

	public void setBallTime(Time ballTime)
    {
    	this.ballTime = ballTime;
    }

	public double getPlayerTime()
    {
    	return playerTime;
    }

	public void setPlayerTime(double playerTime)
    {
    	this.playerTime = playerTime;
    }
}
