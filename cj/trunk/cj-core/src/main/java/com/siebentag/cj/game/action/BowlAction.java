package com.siebentag.cj.game.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.game.BowlModel;
import com.siebentag.cj.game.event.BallReachedBatsmanEvent;
import com.siebentag.cj.game.event.BowlerReleasedBallEvent;
import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.mvc.BallControllerImpl;
import com.siebentag.cj.mvc.BowlerController;
import com.siebentag.cj.mvc.BowlerRole;
import com.siebentag.cj.mvc.BowlerState;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.queue.Scope;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryManager;
import com.siebentag.cj.util.math.TrajectoryPath;

public class BowlAction extends AbstractAction 
{
	private static final Logger log = Logger.getLogger(BowlAction.class);

	@Autowired
	private BowlerController bowlerController;

	@Autowired
	private MovementActionFactory movementActionFactory;

	@Autowired
	private TrajectoryManager trajectoryManager;

	@Autowired
	private BallControllerImpl ballController;

	@Autowired
	private ManagedQueue managedQueue;
	
	private BowlModel bowlModel;

	public BowlAction()
	{
	}
	
	public void run() 
	{
		final double currentTime = getTime();
		final Player bowler = bowlerController.getBowler(BowlerRole.CurrentBowler);

		
		// run to crease
		MovementAction runupAction = movementActionFactory.createRunToAction(
				PersonRole.Bowler, bowler, 
				FieldPosition.BowlOverFastRunUp.getLocation(), 
				FieldPosition.BowlOverRelease.getLocation(), currentTime);
		
		// change state for run-up
		AbstractAction bowlerStateChangeRunUpAction = new AbstractAction() {
			@Override public void run() {
				bowlerController.setState(bowler, BowlerState.RunUp, currentTime);
            }
			@Override public double getTime() {
			    return currentTime;
			}
		};


		// set state to delivery and pause for length of animation
		final double deliveryStartTime = runupAction.getMovement().getCompletionTime();
		final double deliveryFinishTime = deliveryStartTime + 0.4; // TODO get timings from the animation
		AbstractAction bowlerStateChangeDeliveryAction = new AbstractAction() {
			@Override public void run() {
				bowlerController.setState(bowler, BowlerState.Delivery, deliveryStartTime);
            }
			@Override public double getTime() {
			    return deliveryStartTime;
			}
		};


		// release the ball
		final TrajectoryPath trajectoryPath = trajectoryManager.calculate(bowlModel);
		AbstractAction releaseBallAction = new AbstractAction() {
			@Override public void run() {
				ballController.setTrajectoryPath(trajectoryPath, deliveryFinishTime - 0.1);
            }
			@Override public double getTime() {
			    return deliveryFinishTime - 0.1;
			}
		};
		
		
		// alert listeners that the ball has been released
		BowlerReleasedBallEvent bowlerReleasedBallEvent = new BowlerReleasedBallEvent();
		bowlerReleasedBallEvent.setTime(deliveryFinishTime - 0.1);
		
		
		// notify that the ball has reached the batsman
		final double stopRecordingTime = deliveryFinishTime + trajectoryPath.getTimeAtY(-10); // TODO shot terminate y-loc should come from config
		final Point3D ballLocWhenPassingBatsman = trajectoryPath.getLocation(stopRecordingTime);
		boolean hasBounced = trajectoryPath.hasBounced(stopRecordingTime);
		BallReachedBatsmanEvent ballReachedBatsmanEvent = new BallReachedBatsmanEvent(ballLocWhenPassingBatsman, hasBounced);
		ballReachedBatsmanEvent.setTime(stopRecordingTime);
		ballReachedBatsmanEvent.setScope(Scope.Ball);
		
		
		// bowler's follow through
		MovementAction followThroughAction = movementActionFactory.createRunToAction(
				PersonRole.Bowler, bowler, 
				FieldPosition.BowlOverRelease.getLocation(), 
				FieldPosition.BowlOverFollowThrough.getLocation(), deliveryFinishTime);

		// state change for the follow through
		AbstractAction bowlerStateChangeFollowThroughAction = new AbstractAction() {
			@Override public void run() {
				bowlerController.setState(bowler, BowlerState.RunUp, deliveryFinishTime);
            }
			@Override public double getTime() {
			    return deliveryFinishTime;
			}
		};

		final double followThroughFinishTime = followThroughAction.getMovement().getCompletionTime();
		AbstractAction bowlerStateChangeFollowThroughFinishedAction = new AbstractAction() {
			@Override public void run() {
				bowlerController.setState(bowler, BowlerState.Waiting, followThroughFinishTime);
            }
			@Override public double getTime() {
			    return followThroughFinishTime;
			}
		};
		
		log.debug("BowlAction timings:");
		log.debug("  - start time            = " + currentTime);
		log.debug("  - delivery start time   = " + deliveryStartTime);
		log.debug("  - delivery finish time  = " + deliveryFinishTime);
		log.debug("  - follow through finish = " + followThroughFinishTime);
		log.debug("  - ball at batsman       = " + stopRecordingTime);

		
		// queue all the actions
		managedQueue.add(runupAction);
		managedQueue.add(bowlerStateChangeRunUpAction);
		managedQueue.add(bowlerStateChangeDeliveryAction);
		managedQueue.add(releaseBallAction);
		managedQueue.add(bowlerReleasedBallEvent);
		managedQueue.add(ballReachedBatsmanEvent);
		managedQueue.add(followThroughAction);
		managedQueue.add(bowlerStateChangeFollowThroughAction);
		managedQueue.add(bowlerStateChangeFollowThroughFinishedAction);
	}

	@Override public Scope getScope() 
	{
		return Scope.Ball;
	}

	public BowlModel getBowlModel()
    {
    	return bowlModel;
    }

	public void setBowlModel(BowlModel bowlModel)
    {
    	this.bowlModel = bowlModel;
    }
}
