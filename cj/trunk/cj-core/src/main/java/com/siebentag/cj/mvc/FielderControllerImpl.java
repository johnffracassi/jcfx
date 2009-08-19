package com.siebentag.cj.mvc;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.action.AbstractAction;
import com.siebentag.cj.game.action.FielderStateChangeAction;
import com.siebentag.cj.game.action.MovementAction;
import com.siebentag.cj.game.action.MovementActionFactory;
import com.siebentag.cj.game.action.PersonRole;
import com.siebentag.cj.game.event.BallPickedUpEvent;
import com.siebentag.cj.game.event.BallStartedEvent;
import com.siebentag.cj.game.event.InningsStartedEvent;
import com.siebentag.cj.game.event.ShotPlayedEvent;
import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.game.shot.NoConsequence;
import com.siebentag.cj.graphics.renderer.FielderRenderer;
import com.siebentag.cj.model.BoundaryType;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.util.math.BoundaryIntersection;
import com.siebentag.cj.util.math.BoundaryManager;
import com.siebentag.cj.util.math.Calculator;
import com.siebentag.cj.util.math.FielderIntersection;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryManager;
import com.siebentag.cj.util.math.TrajectoryPath;
import com.siebentag.cj.util.math.TrajectoryPoint;

@Component
public class FielderControllerImpl extends PlayerControllerImpl implements FielderController, EventListener
{
    private static final Logger log = Logger.getLogger(FielderControllerImpl.class);

	@Autowired
	private FielderRenderer renderer;
	
	@Autowired
	private BoundaryManager boundaryManager;
	
	@Autowired
	private FieldSettingManager fieldSettingManager;
	
	@Autowired
	private MovementActionFactory movementActionFactory;
	
	@Autowired
	private ManagedQueue managedQueue;
	
	@Autowired
	private TrajectoryManager trajectoryManager;
	
	@Autowired
	private BallController ballController;
	
	@Autowired
	private KeeperController keeperController;
	
	private Map<Player,FielderModel> fielders;
	private FielderModel keeper;
	private FielderModel bowler;
	
	public FielderControllerImpl()
	{
		fielders = new HashMap<Player, FielderModel>();
	}

	public void paint(Graphics2D g, double time)
    {
		for(FielderModel fielder : getFielders())
		{
			if(fielder.isFielding())
			{
				Point3D sLoc = getLocation(fielder.getPlayer(), time);
				renderer.render(g, sLoc, getState(fielder), time - fielder.getTimeOfLastStateChange());
			}
		}
    }

	/**
	 * 
	 * @param time The absolute time that the ball was hit
	 */
	public void fieldBall(ShotResult result, double time)
	{
		log.debug("Field ball (time=" + time + ")");
		
		// check for boundary
		BoundaryIntersection boundaryIntersection = boundaryManager.getBoundaryIntersection(result.getBallPath());
		double boundaryCrossTime = (boundaryIntersection.getType() == BoundaryType.NONE) ? Double.MAX_VALUE : boundaryIntersection.getLocation().getTime();
		log.debug("  boundary=" + boundaryIntersection.getType() + " @ " + boundaryCrossTime);
		
		// find the closest fielder
		FielderIntersection intersection = getClosestFielder(result.getBallPath());
		final double fielderPickupTime = intersection.getBallTime(); // TODO should this be fielder time?
		log.debug("  closest fielder=" + intersection.getPlayer() + " @ " + fielderPickupTime);

		// if the ball crosses the boundary before the fielder can reach it
		if(fielderPickupTime >= boundaryCrossTime)
		{
			intersection = getClosestFielder(boundaryIntersection.getLocation());
		}		
		
		// send a fielder to run to the point of intersection
		Player fielder = intersection.getPlayer();
		MovementAction fielderMovement = movementActionFactory.createRunToAction(PersonRole.Fielder, fielder, getLocation(fielder, time), intersection.getLocation(), time);
		managedQueue.add(fielderMovement);
		
		// is it a catch or a pick-up?
		boolean isCatch = !intersection.getLocation().hasBounced();

		// change the state of the fielder when they pick up the ball
		FielderStateChangeAction fsca = new FielderStateChangeAction();
		fsca.setTime(fielderPickupTime);
		fsca.setFielder(getFielder(fielder));
		fsca.setFielderState(isCatch ? FielderState.Catching : FielderState.PickingUpBallFast);
		managedQueue.add(fsca);

		// notify listeners when the ball is picked up
		BallPickedUpEvent bpue = new BallPickedUpEvent();
		bpue.setFielder(fielder);
		bpue.setCatch(isCatch);
		bpue.setTime(fielderPickupTime);
		managedQueue.add(bpue);		
		
		// now pickup the ball
		if(isCatch)
		{
			// TODO celebrate - throw ball in air, run to centre of wicket
		}
		else
		{
			// return ball to keeper
			final TrajectoryPath returnPath = trajectoryManager.getApproximateSimpleTrajectory(17.5, intersection.getLocation(), FieldPosition.KeeperReturn.getLocation());
			final double returnThrowTime = time + fielderPickupTime + 0.5;
			
			managedQueue.add(new AbstractAction() {
				public void run() {
					ballController.setTrajectoryPath(returnPath, returnThrowTime);
				}
				
				public double getTime() {
					return returnThrowTime;
				}
			});
			
			// move keeper to trajectory end point
			keeperController.moveToStumps(time);
		}
	}
	
	/**
	 * Allocate each fielder a default fielding position (even the keeper and bowler)
	 */
	public void resetForInnings(Team battingTeam, Team fieldingTeam)
	{
		// reset the field positions
		FieldPosition[] fieldSetting = fieldSettingManager.getFieldSetting("default");

		// rebuild the player/fielderModel lookup table
		fielders = new HashMap<Player, FielderModel>();
		for(int i=0; i<11; i++)
		{
			Player player = fieldingTeam.getPlayers().getPlayers().get(i);
			
			FielderModel fielderModel = new FielderModel();
			fielderModel.setPlayer(player);
			fielderModel.setLabel(player.getKey());
			fielderModel.setFieldPosition(fieldSetting[i]);
			fielderModel.setBaseLocation(fielderModel.getFieldPosition().getLocation());
			fielderModel.setFielderState(FielderState.Idle, 0.0);
			fielderModel.setFielding(i > 1); // keeper and bowler shouldn't be painted by this controller
			fielders.put(player, fielderModel);
			
			setLocation(player, fieldSetting[i].getLocation());
		}
		
		// TODO set team colours on the renderer
		
		resetForBall();
	}
	
	/**
	 * Send each fielder back to their fielding positions
	 */
	public void resetForBall()
	{
		for(FielderModel fielder : getFielders())
		{
			setLocation(fielder.getPlayer(), fielder.getBaseLocation());
		}
	}
	
	/**
	 * Assigns a current keeper. Removes the keeper from the control
	 * of the fielder controller, swaps the current keeper (if selected) 
	 * with the fielder that is replacing him.
	 */
	public void assignKeeper(Player player)
	{
		if(keeper == null)
		{
			keeper = fielders.remove(fielders.get(player));
			keeper.setFielding(false);
		}
		else
		{
			// swap the fielder models over
			FielderModel oldKeeper = keeper;
			FielderModel newKeeper = fielders.get(player);
			keeper = fielders.remove(newKeeper);
			fielders.put(oldKeeper.getPlayer(), oldKeeper);
			
			// swap the fielding positions
			FieldPosition oldPosition = newKeeper.getFieldPosition();
			FieldPosition newPosition = oldKeeper.getFieldPosition();
			oldKeeper.setFieldPosition(oldPosition);
			newKeeper.setFieldPosition(newPosition);
			
			oldKeeper.setFielding(true);
			newKeeper.setFielding(false);
		}
	}
	
	/**
	 * Assigns a current bowler. Removes the bowler from the control
	 * of the fielder controller, swaps the current bowler (if selected) 
	 * with the fielder that is replacing him.
	 */
	public void assignBowler(Player player)
	{
		if(bowler == null)
		{
			bowler = fielders.remove(fielders.get(player));
			bowler.setFielding(false);
		}
		else
		{
			// swap the fielder models over
			FielderModel oldBowler = bowler;
			FielderModel newBowler = fielders.get(player);
			bowler = fielders.remove(newBowler);
			fielders.put(oldBowler.getPlayer(), oldBowler);
			
			// swap the fielding positions
			FieldPosition oldPosition = newBowler.getFieldPosition();
			FieldPosition newPosition = oldBowler.getFieldPosition();
			oldBowler.setFieldPosition(oldPosition);
			newBowler.setFieldPosition(newPosition);
			
			oldBowler.setFielding(true);
			newBowler.setFielding(false);
		}
	}
	
	public FielderModel getFielder(Player player)
	{
		return fielders.get(player);
	}

	public List<FielderModel> getFielders()
	{
		return new ArrayList<FielderModel>(fielders.values());
	}
	
	public FielderState getState(FielderModel model)
	{
		return model.getFielderState() == null ? FielderState.Idle : model.getFielderState();
	}
	
	public FielderState getState(Player player)
	{
		if(getFielder(player) != null)
		{
			return getFielder(player).getFielderState();
		}
		else
		{
			return FielderState.Idle;
		}
	}
	
	public void setState(Player player, FielderState fielderState, double time)
	{
		getFielder(player).setFielderState(fielderState, time);
	}

	/**
	 * Find the fielder who can intercept a trajectory within the shortest time
	 * 
	 * @param path
	 * @return
	 */
	public FielderIntersection getClosestFielder(TrajectoryPath path)
	{
		log.debug("finding closest fielder to path (path has " + path.getPoints().size() + " points)");
		
		FielderIntersection intersection = new FielderIntersection();
		double fastestTimeToIntersection = Double.MAX_VALUE;
		
		for(FielderModel fielder : fielders.values())
		{
			if(fielder.isFielding())
			{
				TrajectoryPoint intersectionPoint = calculateFirstPointOfIntersection(fielder, path);

				log.debug("testing how close " + fielder + " is to the path. Intersection=" + intersectionPoint);

				if(intersectionPoint != null)
				{
					double timeToIntersection = calculateTimeToRunTo(fielder, intersectionPoint);
					
					if(timeToIntersection < fastestTimeToIntersection)
					{
						intersection = new FielderIntersection();
						intersection.setLocation(intersectionPoint);
						intersection.setPlayer(fielder.getPlayer());
						intersection.setBallTime(intersectionPoint.getTime());
						intersection.setPlayerTime(timeToIntersection);
						fastestTimeToIntersection = timeToIntersection;
					}

					log.debug(fielder + " will take " + timeToIntersection + "s");
				}
				else
				{
					log.debug(fielder + " can't make it to the path");
				}
				
			}
		}
		
		return intersection;
	}
	
	/**
	 * Find the fielder who can intercept a trajectory within the shortest time
	 * 
	 * @param path
	 * @return
	 */
	public FielderIntersection getClosestFielder(TrajectoryPoint intersectionPoint)
	{
		FielderIntersection intersection = new FielderIntersection();
		double fastestTimeToIntersection = Double.MAX_VALUE;
		
		for(FielderModel fielder : fielders.values())
		{
			if(fielder.isFielding())
			{
				double timeToIntersection = calculateTimeToRunTo(fielder, intersectionPoint);
				
				if(timeToIntersection < fastestTimeToIntersection)
				{
					intersection = new FielderIntersection();
					intersection.setLocation(intersectionPoint);
					intersection.setPlayer(fielder.getPlayer());
					intersection.setBallTime(intersectionPoint.getTime());
					intersection.setPlayerTime(timeToIntersection);
					fastestTimeToIntersection = timeToIntersection;
				}
			}
		}
		
		return intersection;
	}
	
	/**
	 * Calculate the first point in a trajectory path where a fielder
	 * could intercept the ball. If the ball is above the players catchable
	 * height at any time, then that point is not considered reachable.
	 * 
	 * @param fielder
	 * @param path
	 * @return 
	 */
	private TrajectoryPoint calculateFirstPointOfIntersection(FielderModel fielder, TrajectoryPath path)
	{
		for(TrajectoryPoint ballLoc : path.getPoints())
		{
			if(ballLoc.getZ() < 2.25) // TODO get fielder height/reach from attributes
			{
				double timeToRunTo = calculateTimeToRunTo(fielder, ballLoc);
				double ballTravelTime = ballLoc.getTime();
				
				// fielder has to be able to get to the point before the ball can
				if(timeToRunTo < ballTravelTime)
				{
					log.debug("closest PoI for " + fielder + " is " + ballLoc);
					return ballLoc;
				}
			}
		}
		
		log.debug("no path intersection for " + fielder);
		return null;
	}
	
	/**
	 * Calculate the number of seconds it will take for a fielder to run to 
	 * a specified point. 
	 * 
	 * @param fielder
	 * @param destination
	 * @return
	 */
	private double calculateTimeToRunTo(FielderModel fielder, Point3D destination)
	{
		// where is the fielder? TODO this should be the current location, not necessarily the base loc
		Point3D loc = fielder.getFieldPosition().getLocation();
		
		// how far is it?
		double distance = Calculator.distance(loc, destination);
			
		// how fast is the fielder? TODO fielder running speed should come from attributes
		double speed = 7.0;
		
		// how long will it take?
		double time = distance / speed;
		
		return time;
	}
	
	public void event(Event event)
    {
		if(event instanceof ShotPlayedEvent)
		{
			ShotPlayedEvent spe = (ShotPlayedEvent)event;
			
			if(!(spe.getShotResult().getConsequence() instanceof NoConsequence))
			{
				fieldBall(spe.getShotResult(), spe.getTime());
			}
		} 
		else if(event instanceof BallStartedEvent) 
		{
			resetForBall();
		}
		else if(event instanceof InningsStartedEvent) 
		{
			InningsStartedEvent isev = (InningsStartedEvent)event;
			resetForInnings(isev.getBatting(), isev.getFielding());
		}
    }

	public Class<?>[] register()
    {
	    return new Class<?>[] {
	    	ShotPlayedEvent.class, BallStartedEvent.class, InningsStartedEvent.class, InningsStartedEvent.class
	    };
    }
}
