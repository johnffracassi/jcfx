package com.siebentag.cj.game.event;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("ShotController")
@Consumer("Umpire")
public class BallPickedUpEvent extends AbstractEvent
{
	private Player fielder;
	private boolean isCatch;
	
	public BallPickedUpEvent()
	{
	}
	
    public String getDescription()
    {
	    return "Ball picked up - " + fielder;
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
}
