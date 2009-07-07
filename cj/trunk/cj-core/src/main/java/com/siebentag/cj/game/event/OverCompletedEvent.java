/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("Umpire")
@Consumer("")
public class OverCompletedEvent extends AbstractEvent
{
	Player bowler;
	
	public OverCompletedEvent(Player bowler)
	{
		this.bowler = bowler;
	}
	
	public Player getBowler()
	{
		return bowler;
	}
	
    public String getDescription()
    {
	    return "End of Over";
    }

	@Override
    public void cancel()
    {
    }
}
