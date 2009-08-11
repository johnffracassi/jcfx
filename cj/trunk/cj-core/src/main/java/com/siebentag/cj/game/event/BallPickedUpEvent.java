package com.siebentag.cj.game.event;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.queue.AbstractEvent;
import com.siebentag.cj.util.math.Point3D;

@Producer("ShotController")
@Consumer("Umpire")
public class BallPickedUpEvent extends AbstractEvent
{
	private Point3D location;
	private Player fielder;
	private boolean isCatch;
	
	public BallPickedUpEvent()
	{
	}
	
    public String getDescription()
    {
	    return "Ball picked up by " + fielder + " at " + location;
    }

    public boolean isCatch()
    {
    	return isCatch;
    }
    
    public Player getFielder()
    {
    	return fielder;
    }

	public void setFielder(Player fielder)
    {
    	this.fielder = fielder;
    }

	public void setCatch(boolean isCatch)
    {
    	this.isCatch = isCatch;
    }

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}
}
