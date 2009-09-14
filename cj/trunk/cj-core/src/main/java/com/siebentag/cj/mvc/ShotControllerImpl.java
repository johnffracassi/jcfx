package com.siebentag.cj.mvc;

import java.awt.geom.Point2D;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.event.BallCompletedEvent;
import com.siebentag.cj.game.event.BallPickedUpEvent;
import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.game.shot.Bowled;
import com.siebentag.cj.game.shot.Consequence;
import com.siebentag.cj.game.shot.EdgeToKeeper;
import com.siebentag.cj.game.shot.Hit;
import com.siebentag.cj.game.shot.HitBatsman;
import com.siebentag.cj.game.shot.LBWAppeal;
import com.siebentag.cj.game.shot.NoConsequence;
import com.siebentag.cj.game.shot.ShotAnalyser;
import com.siebentag.cj.game.shot.ShotModel;
import com.siebentag.cj.game.shot.ShotRecorder;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TrajectoryManager;
import com.siebentag.cj.util.math.TrajectoryModel;
import com.siebentag.cj.util.math.TrajectoryPath;

@Component
public class ShotControllerImpl implements ShotController
{
	private static final Logger log = Logger.getLogger(ShotControllerImpl.class);

	@Autowired
	private ShotRecorder shotRecorder; 

	@Autowired
	private ShotAnalyser shotAnalyser;
	
	@Autowired 
	private KeeperController keeperController;
	
	@Autowired 
	private BatsmanController batsmanController;
	
	@Autowired
	private BallController ballController;
	
	@Autowired
	private ManagedQueue managedQueue;
	
	@Autowired
	private TrajectoryManager trajectoryManager;
	
	public ShotResult playShot(Player batsman, Point3D initialLoc, Time time)
	{
		// get recorded shot
		List<Point2D> shotPoints = shotRecorder.getShotPoints();
		log.debug("getting recorded shot points (" + shotPoints.size() + ")");
		shotRecorder.reset();
		
		// find out some information about where the ball is
		TrajectoryPath ballPath = ballController.getTrajectoryPath();
		Time ballTimeAtBatsman = ballPath.getTimeAtY(FieldPosition.BatStrikerRight.getLocation().getY());
		double velocityAtBatsman = ballPath.getVelocityAtTime(ballTimeAtBatsman);

		// analyse shot
		ShotModel attemptedShotModel = shotAnalyser.analyse(shotPoints);
		attemptedShotModel.setVelocity(velocityAtBatsman);
		
		// select shot and consequence
		ShotModel actualShotModel = shotAnalyser.chooseShot(batsman, initialLoc, attemptedShotModel);
		Consequence consequence = actualShotModel.getConsequence();

		// result object. default to ball going through to keeper
		ShotResult shotResult = new ShotResult();
		TrajectoryPath newBallPath = null;

		if(consequence instanceof HitBatsman)
		{
			// TODO drop close to batsman
			// TODO lower batsman confidence
			shotResult.setOffBat(false);
			shotResult.setOffBody(true);
			newBallPath = ballPath.subPath(ballTimeAtBatsman);
		}
		else if(consequence instanceof LBWAppeal)
		{
			// TODO check for lbw
			newBallPath = ballPath.subPath(ballTimeAtBatsman);
		}
		else if(consequence instanceof Hit)
		{
			// create a trajectory based on the shot
			TrajectoryModel trajectory = actualShotModel.getTrajectory();
			trajectory.setOrigin(initialLoc);
			newBallPath = trajectoryManager.calculate(trajectory);
			shotResult.setOffBat(true);
			shotResult.setOffBody(false);
		}
		else if(consequence instanceof Bowled)
		{
			// TODO alert the stump manager
			// TODO alert the umpire that a wicket has fallen
			// TODO alter the path of the ball after it hits the stumps
			shotResult.setOffBat(false);
			newBallPath = ballPath.subPath(ballTimeAtBatsman);
		}
		else if(consequence instanceof NoConsequence)
		{
			newBallPath = ballPath.subPath(ballTimeAtBatsman);
			batsmanController.setState(batsman, BatsmanState.LeaveROff, time);
			ballThroughToKeeper(ballPath, time, false);
		}
		else if(consequence instanceof EdgeToKeeper)
		{
			newBallPath = ballPath.subPath(ballTimeAtBatsman);
			ballThroughToKeeper(ballPath, time, false);
		}
		
		shotResult.setConsequence(consequence);
		shotResult.setTrajectoryPath(newBallPath);
		
		ballController.setTrajectoryPath(newBallPath, time);
		
		return shotResult;
	}
	
	private void ballThroughToKeeper(TrajectoryPath ballPath, Time time, boolean isCatch) 
	{
		// move the keeper to the path of the ball
		keeperController.moveToLineOfBall(ballPath, time);
		
		// TODO does the keeper take it cleanly? unclean takes for shit keepers 
		
		// TODO terminate the ball path when it reaches the keeper			
		Time timeBallReachesKeeper = ballPath.getTimeAtY(keeperController.getFieldPosition().getLocation().getY());
		ballPath.setTerminateTime(timeBallReachesKeeper);
		
		// notify listeners when the ball is caught
		BallPickedUpEvent bpue = new BallPickedUpEvent();
		bpue.setFielder(keeperController.getKeeper());
		bpue.setCatch(isCatch);
		bpue.setTime(timeBallReachesKeeper);
		bpue.setLocation(ballPath.getLocation(timeBallReachesKeeper));
		managedQueue.add(bpue);
		
		// raise a ball completed event
		BallCompletedEvent bce = new BallCompletedEvent();
		bce.setTime(time.add(timeBallReachesKeeper));
		managedQueue.add(bce);
	}
}
