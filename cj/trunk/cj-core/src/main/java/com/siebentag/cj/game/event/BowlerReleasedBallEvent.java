package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;

public class BowlerReleasedBallEvent extends AbstractEvent
{
	public BowlerReleasedBallEvent()
	{
	}

    public String getDescription()
    {
	    return "Ball released by bowler";
    }
}
