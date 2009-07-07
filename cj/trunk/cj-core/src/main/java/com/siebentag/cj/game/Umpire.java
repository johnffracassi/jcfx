package com.siebentag.cj.game;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.event.BallPickedUpEvent;
import com.siebentag.cj.game.event.BallReachedBatsmanEvent;
import com.siebentag.cj.game.event.BallStartedEvent;
import com.siebentag.cj.game.event.BatsmanCompletedRunEvent;
import com.siebentag.cj.game.event.MovementCompletedEvent;
import com.siebentag.cj.game.event.OverCompletedEvent;
import com.siebentag.cj.game.event.ScoringEvent;
import com.siebentag.cj.game.event.ShotPlayedEvent;
import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.game.shot.Bowled;
import com.siebentag.cj.game.shot.Consequence;
import com.siebentag.cj.model.Ball;
import com.siebentag.cj.model.BoundaryType;
import com.siebentag.cj.model.Extra;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Wicket;
import com.siebentag.cj.model.WicketType;
import com.siebentag.cj.mvc.BatsmanController;
import com.siebentag.cj.mvc.BatsmanRole;
import com.siebentag.cj.mvc.BowlerController;
import com.siebentag.cj.mvc.BowlerRole;
import com.siebentag.cj.mvc.KeeperController;
import com.siebentag.cj.mvc.ShotResult;
import com.siebentag.cj.mvc.UmpireController;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.util.math.Point3D;

/**
 * 
 * Listens for: BallStartedEvent, MovementCompletedEvent, BallTakenByKeeperEvent, BallPickedUpEvent
 * 				BallReachedBatsmanEvent
 * Raises: ScoringEvent, OverCompletedEvent
 * 
 */
@Component
public class Umpire implements EventListener
{
	@Autowired
	private UmpireController umpireController;
	
	@Autowired
	private BatsmanController batsmanController;

	@Autowired
	private BowlerController bowlerController;

	@Autowired
	private KeeperController keeperController;
	
	@Autowired
	private ManagedQueue managedQueue;
	
	private int ballsBowledInOver;
	private int strikerRunsCompleted;
	private int nonStrikerRunsCompleted;
	private BoundaryType boundaryType;
	private Wicket wicket;
	private boolean noball;
	private boolean wide;
//	private boolean freeHit;
	private boolean legByes;
	private boolean byes;
	
	private Player striker;
	private Player nonStriker;
	private Player bowler;
	private Player fielder;
	private Player keeper;
	
	public Class<?>[] register()
    {
	    return new Class<?>[] {
	    	BallStartedEvent.class, MovementCompletedEvent.class, 
	    	BallPickedUpEvent.class, BallReachedBatsmanEvent.class,
	    	ShotPlayedEvent.class
	    };
    }

	public void event(Event event)
    {
		if(event instanceof BallStartedEvent)
		{
			handleBallStartedEvent((BallStartedEvent)event);
		}
		else if(event instanceof MovementCompletedEvent)
		{
			handleMovementCompletedEvent((MovementCompletedEvent)event);
		}
		else if(event instanceof BallPickedUpEvent)
		{
			handleBallPickedUpEvent((BallPickedUpEvent)event);
		}
		else if(event instanceof BallReachedBatsmanEvent)
		{
			handleBallReachedBatsmanEvent((BallReachedBatsmanEvent)event);
		}
		else if(event instanceof ShotPlayedEvent)
		{
			handleShotPlayedEvent((ShotPlayedEvent)event);
		}
    }
	
	private void handleShotPlayedEvent(ShotPlayedEvent event)
    {
		ShotResult shotResult = event.getShotResult();
		Consequence consequence = shotResult.getConsequence();
		
		Log.info("Shot played. Consequence=" + consequence);
		
		if(consequence instanceof Bowled)
		{
			wicket = new Wicket();
			wicket.setBatsman(striker);
			wicket.setIsOut(true);
			wicket.setWicketType(WicketType.BOWLED);
		}
    }

	private void handleBallStartedEvent(BallStartedEvent event)
	{
		resetForBall();
	}
	
	private void handleBallPickedUpEvent(BallPickedUpEvent event)
	{
		fielder = event.getFielder();
		
		if(event.isCatch())
		{
			ballCaughtByFielder();
		}
	}
	
	private void handleMovementCompletedEvent(MovementCompletedEvent event)
	{
		Player player = event.getMovement().getPerson();
		Point3D destination = event.getMovement().getDestination();
			
		if(player.equals(striker) || player.equals(nonStriker))
		{
			if(player.equals(striker))
			{
				if(destination.equals(FieldPosition.BatRunStrikerOdd))
				{
					if(strikerRunsCompleted % 2 == 0)
					{
						// striker running to the bowlers end
						strikerRunsCompleted ++;
						managedQueue.add(new BatsmanCompletedRunEvent(BatsmanRole.Striker));
					}
				}
				else if(destination.equals(FieldPosition.BatRunStrikerEven))
				{
					if(strikerRunsCompleted % 2 == 1)
					{
						// striker running to the keepers end
						strikerRunsCompleted ++;
						managedQueue.add(new BatsmanCompletedRunEvent(BatsmanRole.Striker));
					}
				}
			}
			else if(player.equals(striker))
			{
				if(destination.equals(FieldPosition.BatRunNonStrikerOdd))
				{
					if(nonStrikerRunsCompleted % 2 == 0)
					{
						// non-striker running to the keepers end
						nonStrikerRunsCompleted ++;
						managedQueue.add(new BatsmanCompletedRunEvent(BatsmanRole.NonStriker));
					}
				}
				else if(destination.equals(FieldPosition.BatRunNonStrikerEven))
				{
					if(nonStrikerRunsCompleted % 2 == 1)
					{
						// non-striker running to the bowlers end
						nonStrikerRunsCompleted ++;
						managedQueue.add(new BatsmanCompletedRunEvent(BatsmanRole.NonStriker));
					}
				}
			}
			
			// check if a full run has been completed
			if(strikerRunsCompleted == nonStrikerRunsCompleted)
			{
				// TODO raise scoring event
			}
		}
	}
	
	/**
	 * TODO these outer boundaries need to come from some sort of config file
	 */
	private void handleBallReachedBatsmanEvent(BallReachedBatsmanEvent event)
	{
		Point3D loc = event.getLocation();
		boolean hasBounced = event.hasBounced();
		
		// check for no-ball (above waist full toss)
		if(!hasBounced && loc.getZ() > 0.9)
		{
			noball = true;
		}
		
		// check for wide (too high)
		if(loc.getZ() > 2.0)
		{
			wide = true;
		}
		
		// is too wide
		if(loc.getX() > 1.2 || loc.getX() < -0.7)
		{
			wide = true;
		}
	}
	
	private void handleBallCompletedEvent()
	{
		Ball ball = buildBall();
		
		if(ball.getBallCountsForBowler() > 0)
		{
			ballsBowledInOver ++;
		}
		
		managedQueue.add(new ScoringEvent(ball));
		
		if(ballsBowledInOver == 6)
		{
			overCompleted();
		}
	}
	
	private void ballCaughtByFielder()
	{
		wicket = new Wicket();
		wicket.setBatsman(striker);
		wicket.setIsOut(true);
		wicket.setWicketType(WicketType.CAUGHT);
	}
	
	private void overCompleted()
	{
		OverCompletedEvent oce = new OverCompletedEvent(bowler);
		managedQueue.add(oce);
		
		ballsBowledInOver = 0;
	}
	
	private Ball buildBall()
	{
		Ball ball = new Ball();
		
		ball.setStriker(striker);
		ball.setNonStriker(nonStriker);
		ball.setBowler(bowler);
		ball.setBallCountsForBatsman((noball || wide) ? 0 : 1);
		ball.setBallCountsForBowler(1);
		ball.setBoundaryType(boundaryType);
		ball.setFielder(fielder);
		ball.setKeeper(keeper);
		ball.setOrdinal(ballsBowledInOver);
		ball.setWicket(wicket);
		ball.setExtra(getExtras());
		ball.setRunsForBatsman((byes || legByes) ? 0 : getRunsCompleted());
		ball.setRunsForBowler((byes || legByes) ? 0 : getRunsCompleted());
		ball.setRunsForTeam(getRunsCompleted());
		
		return ball;
	}
	
	private Extra getExtras()
	{
		if(wide || noball || byes || legByes)
		{
			Extra extra = new Extra();
			extra.setBye((byes && !wide) ? getRunsCompleted() : 0);
			extra.setLegbye(legByes ? getRunsCompleted() : 0);
			extra.setNoball(noball ? 1 : 0);
			extra.setWide(wide ? (1 + getRunsCompleted()) : 0);
			extra.setOther(0);
			return extra;
		}
		else
		{
			return null;
		}
	}
	
	private int getRunsCompleted()
	{
		return Math.min(strikerRunsCompleted, nonStrikerRunsCompleted);
	}
	
	private void resetForBall()
	{
		strikerRunsCompleted = 0;
		nonStrikerRunsCompleted = 0;
		boundaryType = BoundaryType.NONE;
		wicket = null;
		byes = false;
		legByes = false;
		wide = false;
		noball = false;
		
		striker = batsmanController.getBatsman(BatsmanRole.Striker);
		nonStriker = batsmanController.getBatsman(BatsmanRole.NonStriker);
		bowler = bowlerController.getBowler(BowlerRole.CurrentBowler);
		keeper = keeperController.getKeeper();
		fielder = null;
	}
}
