package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;
import com.siebentag.cj.queue.Priority;
import com.siebentag.cj.util.math.Point3D;

@Producer("BowlAction")
@Consumer("ShotController")
public class BallReachedBatsmanEvent extends AbstractEvent
{
	private Point3D loc;
	private boolean bounced;
	
	public BallReachedBatsmanEvent(Point3D loc, boolean hasBounced)
	{
		this.loc = loc;
		this.bounced = hasBounced;
	}

	public boolean hasBounced()
	{
		return bounced;
	}
	
	public Point3D getLocation()
	{
		return loc;
	}
	
    public String getDescription()
    {
	    return "Ball passing batsman";
    }

    @Override
    public Priority getPriority()
    {
        return Priority.Low;
    }
    
	@Override
    public void cancel()
    {
    }
}
