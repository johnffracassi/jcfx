package com.siebentag.cj;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.event.BallCompletedEvent;
import com.siebentag.cj.game.event.BallReachedBatsmanEvent;
import com.siebentag.cj.game.event.BallStartedEvent;
import com.siebentag.cj.game.event.BowlerReleasedBallEvent;
import com.siebentag.cj.game.event.ShotPlayedEvent;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.mvc.BatsmanController;
import com.siebentag.cj.mvc.BatsmanRole;
import com.siebentag.cj.mvc.ShotController;
import com.siebentag.cj.mvc.ShotResult;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.queue.ManagedQueue;

@Component
public class GameStateManager implements EventListener
{
	private static final Logger log = Logger.getLogger(GameStateManager.class);

	private boolean performRendering = true;
	private boolean recordingShot = false;
	private boolean allowRunning = false;
	private boolean ballInProgress = false;
	
	@Autowired
	private ShotController shotController;

	@Autowired
	private BatsmanController batsmanController;
	
	@Autowired
	private ManagedQueue managedQueue;
	
	public void event(Event event)
    {
		if(event instanceof BallStartedEvent)
		{
			ballInProgress = true;
			allowRunning = false;
			recordingShot = false;
			log.debug("not recording shot, no running, ball in progress");
		}
		else if(event instanceof BallCompletedEvent)
		{
			ballInProgress = false;
			allowRunning = false;
			recordingShot = false;
			log.debug("not recording shot, no running, ball is dead");
		}
		else if(event instanceof BallReachedBatsmanEvent)
		{
			BallReachedBatsmanEvent brbe = (BallReachedBatsmanEvent)event;
			Player batsman = batsmanController.getBatsman(BatsmanRole.Striker);
			ShotResult shotResult = shotController.playShot(batsman, brbe.getLocation(), event.getTime());

			ballInProgress = true;
			allowRunning = true;
			recordingShot = false;
			
			log.debug("stopped recording shot, allowing running, ball in progress");
			
			ShotPlayedEvent spe = new ShotPlayedEvent();
			spe.setTime(event.getTime());
			spe.setShotResult(shotResult);
			managedQueue.add(spe);
		}
		else if(event instanceof BowlerReleasedBallEvent)
		{
			ballInProgress = true;
			allowRunning = false;
			recordingShot = true;
			log.debug("start recording shot, no running, ball in progress");
		}
    }

	public Class<?>[] register()
    {
	    return new Class<?>[] {
	    	BallStartedEvent.class, BallCompletedEvent.class, 
	    	BallReachedBatsmanEvent.class, BowlerReleasedBallEvent.class
	    };
    }	
	
	public boolean isRecordingShot() 
	{
		return recordingShot;
	}

	public boolean isPerformRendering() 
	{
		return performRendering;
	}

	public boolean isRunningAllowed()
	{
		return allowRunning;
	}
	
	public boolean isBallInProgress()
    {
    	return ballInProgress;
    }
}
