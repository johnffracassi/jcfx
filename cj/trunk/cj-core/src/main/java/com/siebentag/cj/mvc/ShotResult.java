package com.siebentag.cj.mvc;

import com.siebentag.cj.game.shot.Consequence;
import com.siebentag.cj.util.math.TrajectoryPath;

public class ShotResult
{
	private boolean isOffBat;
	private boolean isOffBody;
	private TrajectoryPath ballPath;
	private Consequence consequence;

	public boolean isOffBat()
	{
		return isOffBat;
	}

	public void setOffBat(boolean isOffBat)
	{
		this.isOffBat = isOffBat;
	}

	public boolean isOffBody()
	{
		return isOffBody;
	}

	public void setOffBody(boolean isOffBody)
	{
		this.isOffBody = isOffBody;
	}

	public TrajectoryPath getBallPath()
	{
		return ballPath;
	}

	public void setTrajectoryPath(TrajectoryPath ballPath)
	{
		this.ballPath = ballPath;
	}

	public Consequence getConsequence()
    {
    	return consequence;
    }

	public void setConsequence(Consequence consequence)
    {
    	this.consequence = consequence;
    }

}
