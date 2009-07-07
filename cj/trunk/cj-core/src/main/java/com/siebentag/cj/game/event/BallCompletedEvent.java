/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;

/**
 * @author jeff
 *
 */
public class BallCompletedEvent extends AbstractEvent
{
	public BallCompletedEvent()
	{
	}
	
    public String getDescription()
    {
	    return "End of ball";
    }
}
