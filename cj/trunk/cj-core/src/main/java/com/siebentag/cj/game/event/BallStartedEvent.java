/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;

/**
 * @author jeff
 *
 */
public class BallStartedEvent extends AbstractEvent
{
	public BallStartedEvent()
	{
	}
	
    public String getDescription()
    {
	    return "Start of ball";
    }
}
