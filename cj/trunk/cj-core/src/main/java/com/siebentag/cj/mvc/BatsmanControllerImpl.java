package com.siebentag.cj.mvc;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.action.MovementAction;
import com.siebentag.cj.game.action.MovementActionFactory;
import com.siebentag.cj.game.action.PersonRole;
import com.siebentag.cj.game.event.BallCompletedEvent;
import com.siebentag.cj.game.event.BatsmanCompletedRunEvent;
import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.graphics.renderer.BatsmanRenderer;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

@Component
public class BatsmanControllerImpl 
	extends PlayerControllerImpl 
	implements BatsmanController, EventListener 
{
	private Map<BatsmanRole,Player> playerByRole;
	private Map<Player,BatsmanState> stateByPlayer;
	private Map<BatsmanRole,Integer> runsQueued;
	private Map<BatsmanRole,Integer> runsCompleted;
	
	@Autowired
	private BatsmanRenderer renderer;
	
	@Autowired
	private BowlerController bowlerController;
	
	@Autowired
	private ManagedQueue managedQueue;
	
	@Autowired
	private MovementActionFactory movementActionFactory;
	
	public BatsmanControllerImpl()
	{
		super();
		
		playerByRole = new HashMap<BatsmanRole, Player>();
		stateByPlayer = new HashMap<Player, BatsmanState>();
		runsQueued = new HashMap<BatsmanRole, Integer>();
		runsCompleted = new HashMap<BatsmanRole, Integer>();
	}
	
	public void resetForInnings(Team battingTeam, Team fieldingTeam)
	{
		setBatsman(battingTeam.getPlayers().getPlayers().get(0), BatsmanRole.Striker);
		setBatsman(battingTeam.getPlayers().getPlayers().get(1), BatsmanRole.NonStriker);

		// TODO set the colours
		
		resetForBall();
	}
	
	public void resetForBall()
	{
		Player striker = getBatsman(BatsmanRole.Striker);
		Player nonStriker = getBatsman(BatsmanRole.NonStriker);
		
		resetRunCounters();

		setState(striker, BatsmanState.StrikerWaiting, Time.ZERO);
		setState(nonStriker, BatsmanState.Idle, Time.ZERO);
		
		// set the striker location
		setLocation(striker, FieldPosition.BatStrikerRight.getLocation());
		
		// set the non-striker location
		if(bowlerController != null)
		{
			if(bowlerController.getBowlingSide() == BowlingSide.RightArmOver || bowlerController.getBowlingSide() == BowlingSide.LeftArmAround)
			{
				setLocation(nonStriker, FieldPosition.BatNonStrikerOver.getLocation());
			}
			else
			{
				setLocation(nonStriker, FieldPosition.BatNonStrikerAround.getLocation());
			}
		}
	}
	
	private void resetRunCounters()
	{
		for(BatsmanRole role : BatsmanRole.values())
		{
			runsQueued.put(role, 0);
			runsCompleted.put(role, 0);
		}
	}
	
	public void paint(Graphics2D g, Time time)
	{
		for(BatsmanRole role : BatsmanRole.values())
		{
			Player batsman = getBatsman(role);
			Point3D sLoc = getLocation(batsman, time);
			renderer.render(g, sLoc, getState(batsman), getTimeSinceStateChange(batsman, time));
		}		
	}
	
	/**
	 * Check each batsman, see if they are required to run again
	 */
	private void primeRunQueue(Time time)
	{
		for(BatsmanRole role : BatsmanRole.values())
		{
			primeBatsman(role, time);
		}
	}

	/**
	 * If the batsman has more runs queued, then make him run.
	 * Will only trigger the batsman to run if he is idle.
	 */
	private void primeBatsman(BatsmanRole role, Time time)
	{
		int numRunsCompleted = runsCompleted.get(role);
		
		// if the batsman is idle, and there are more runs queued, then initiate a run
		if(hasRunsQueued(role) && !isRunning(role))
		{
			// which end should the batsman be sent?
			FieldPosition destination = null;
			
			if(role == BatsmanRole.Striker)
			{
				destination = (numRunsCompleted % 2 == 1) ? FieldPosition.BatRunStrikerEven : FieldPosition.BatRunStrikerOdd;
			}
			else if(role == BatsmanRole.NonStriker)
			{
				destination = (numRunsCompleted % 2 == 1) ? FieldPosition.BatRunNonStrikerEven : FieldPosition.BatRunNonStrikerOdd;
			}

			// perform the run
			Time runStartTime = time.add(0.15); // delay the run a little bit
			MovementAction action = movementActionFactory.createRunToAction(PersonRole.Batsman, getBatsman(role), getLocation(getBatsman(role), time), destination.getLocation(), runStartTime);
			managedQueue.add(action);
		}
	}
	
	/**
	 * Has the batsman got more runs to complete?
	 */
	private boolean hasRunsQueued(BatsmanRole role)
	{
		int numRunsQueued = runsQueued.get(role);
		int numRunsCompleted = runsCompleted.get(role);
		
		return (numRunsQueued > numRunsCompleted);
	}
	
	/**
	 * Increment the queued run counter for each batsman
	 */
	public void queueRun(Time time) 
	{
		for(BatsmanRole role : BatsmanRole.values())
		{
			runsQueued.put(role, runsQueued.get(role) + 1);
		}
		
		primeRunQueue(time);
	}

	/**
	 * Reset the queued run count for each batsman
	 */
	public void cancelQueuedRuns() 
	{
		for(BatsmanRole role : BatsmanRole.values())
		{
			runsQueued.put(role, 0);
		}
		
		// TODO send batsmen back to the nearest end
	}

	/**
	 * dequeue a run, and run again if another run is queued
	 */
	private void handleBatsmanCompletedRunEvent(BatsmanCompletedRunEvent event)
	{
		// decrease the number of queued runs for this batsman
		int numRunsQueued = runsQueued.get(event.getRole());
		runsQueued.put(event.getRole(), numRunsQueued - 1);
		
		// set his state to idle now that he has finished running
		setState(getBatsman(event.getRole()), BatsmanState.Idle, event.getTime());
		
		// trigger the batsman to run if there are more runs queued
		primeBatsman(event.getRole(), event.getTime());
	}

	private void handleBallCompletedEvent(BallCompletedEvent event)
	{
		// set the batsmen to idle, waiting for the next ball
		setState(getBatsman(BatsmanRole.Striker), BatsmanState.Idle, event.getTime());
		setState(getBatsman(BatsmanRole.NonStriker), BatsmanState.Idle, event.getTime());
	}
	
	/**
	 * Determine if the batsman is currently performing a run (based on state)
	 */
	private boolean isRunning(BatsmanRole role)
	{
		return getState(getBatsman(role)) == BatsmanState.Running;
	}
	
	/**
	 * Decorate the move action, informs this controller of the state change
	 */
	@Override
	public void doMove(PersonMovement movement)
	{
		// if the move style is Run, then set the batsman state to Running
		Player player = movement.getPerson();
		BatsmanState state = (movement.getMoveStyle() == MoveStyle.Run ? BatsmanState.Running : getState(player));
		Time time = movement.getStartTime();
		
		// inform the batsman controller of the state change
	    setState(player, state, time);
		
	    // perform the actual move
	    super.doMove(movement);
	}
	
	public void event(Event event)
    {
		if(event instanceof BatsmanCompletedRunEvent)
		{
			handleBatsmanCompletedRunEvent((BatsmanCompletedRunEvent)event);
		}
		else if(event instanceof BallCompletedEvent)
		{
			handleBallCompletedEvent((BallCompletedEvent)event);
		}
    }

	public Class<?>[] register()
    {
	    return new Class<?>[] { 
	    		BatsmanCompletedRunEvent.class, BallCompletedEvent.class
	    };
    }
	
	public Player getStriker()
	{
		return getBatsman(BatsmanRole.Striker);
	}
	
	public Player getNonStriker()
	{
		return getBatsman(BatsmanRole.NonStriker);
	}
	
	public Player getBatsman(BatsmanRole role) 
	{
		return playerByRole.get(role);
	}

	public BatsmanState getState(Player player) 
	{
		if(stateByPlayer.containsKey(player))
		{
			return stateByPlayer.get(player);
		}
		else
		{
			return BatsmanState.Idle;
		}
	}

	public void setBatsman(Player player, BatsmanRole role) 
	{
		playerByRole.put(role, player);
	}

	public void setState(Player player, BatsmanState state, Time time) 
	{
		stateByPlayer.put(player, state);
		setStateChangeTime(player, time);
	}
}
