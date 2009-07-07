package com.siebentag.cj.util.math;

import com.siebentag.cj.model.Player;

public class FielderIntersection
{
	private TrajectoryPoint location;
	private double ballTime;
	private Player player;
	private double playerTime;

	public FielderIntersection()
	{
		location = null;
		player = null;
		ballTime = 0.0;
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

	public double getBallTime()
    {
    	return ballTime;
    }

	public void setBallTime(double ballTime)
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
