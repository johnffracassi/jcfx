/**
 * 
 */
package com.siebentag.cj.game.shot;

import static java.lang.Math.sqrt;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.World;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.format.Formatter;
import com.siebentag.cj.util.math.Calculator;
import com.siebentag.cj.util.math.Point3D;

@Component
public class ShotAnalyser
{
	private static final Logger log = Logger.getLogger(ShotAnalyser.class);
	
	@Autowired
	private World world;
	
	private Shots shots = null;
	
	public ShotAnalyser()
	{
		init();
	}
	
	public void init()
	{
		ShotsDigester digester = new ShotsDigester();
		digester.loadShots();
		shots = digester.getShots();
	}
	
	
	/**
	 * Choose a valid shot
	 * 
	 * @param ballLoc
	 * @param model
	 * @return
	 */
	public ShotModel chooseShot(Player batsman, Point3D ballLoc, ShotModel desiredModel)
	{
		log.debug("Ball loc at batsman: " + ballLoc);
		
		if(desiredModel != null)
		{
			double ang = desiredModel.getDesiredAngle();
			double pow = desiredModel.getDesiredPower();
			double elv = desiredModel.getDesiredElevation();
			
			// find any shot that has a matching zone (not including the default zone)
			List<Shot> candidates = getCandidateShots(ballLoc, ang, elv, pow);
			
			// pick one of the candidate shots
			Shot shot = chooseRandomShot(candidates);

			if(shot != null)
			{
				// build the actual shot model
				ShotModel model = playShot(batsman, desiredModel, shot, ballLoc);
				return model;
			}
			else
			{
				return null;
			}
		}
		else
		{
			log.debug("no shot offered");

			ShotModel model = playShot(batsman, desiredModel, shots.getNullShot(), ballLoc);
			return model;
		}
	}
	
	/**
	 * 
	 * @param shots
	 * @return
	 */
	private Shot chooseRandomShot(List<Shot> shots)
	{
		log.debug("randomly choosing one of the " + shots.size() + " shot(s)");
		
		if(shots != null && shots.size() > 0)
		{
			int idx = (int)(Math.random() * (double)shots.size());
			return shots.get(idx);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param batsman
	 * @param desiredShotModel
	 * @param shot
	 * @param loc
	 * @return
	 */
	private ShotModel playShot(Player batsman, ShotModel desiredShotModel, Shot shot, Point3D loc)
	{
		log.debug("playing the shot - " + shot);
		
		Consequence con = chooseConsequence(batsman, shot, loc);
		log.debug("Consequence => " + con);

		ShotModel actualShotModel = new ShotModel();

		if(desiredShotModel != null) // no shot offered
		{
			actualShotModel.setShot(shot);
			actualShotModel.setConsequence(con);
			
			if(con instanceof Hit)
			{
				Hit hit = (Hit)con;
				
				double newAng = applyAccuracyMod(desiredShotModel.getDesiredAngle(), hit.getAccuracy());
				actualShotModel.setAngle(newAng);
				log.debug("=> angle - desired=" + desiredShotModel.getDesiredAngle() + " shot=" + newAng);

				actualShotModel.setElevation(desiredShotModel.getDesiredElevation());
				log.debug("=> elev  - desired=" + desiredShotModel.getDesiredElevation() + " shot=" + actualShotModel.getElevation());
				
				double pow = hit.getPower() * desiredShotModel.getDesiredPower();
				log.debug("=> power - desired=" + desiredShotModel.getDesiredPower() + " shot=" + hit.getPower() + "*" + desiredShotModel.getDesiredPower());
				actualShotModel.setPower(pow);
				
				double velocity = desiredShotModel.getVelocity() * pow;
				actualShotModel.setVelocity(velocity);
			}
		}
		else
		{
			actualShotModel.setConsequence(con);
		}
		
		return actualShotModel;
	}
	
	private double applyAccuracyMod(double angleDegrees, double acc)
	{
		double err = (1.0 - acc);
		double diff = err * 360.0;
		double change = Math.random() * diff;
		return Math.toRadians(Math.random() < 0.5 ? angleDegrees + change : angleDegrees - change); 
	}
	
	/**
	 * 
	 * 
	 * @param batsman
	 * @param desired
	 * @param shot
	 * @param loc
	 * @return
	 */
	private Consequence chooseConsequence(Player batsman, Shot shot, Point3D loc)
	{
		double ability = 0.8;
//		double ability = batsman.getAttributes().getBatting().getAbility();

		Consequence con = new NoConsequence();
		
		// test all the zones that the ball may pass through
		for(Zone zone : shot.getZones().getZones(loc.getX(), loc.getZ()))
		{
			double rand = Math.random() * 100.0;
			double prob = zone.getProbability().calculate(ability);
			
			log.debug(zone.getPriority() + ") testing probability of " + zone + " (" + Formatter.format(rand) + " < " + Formatter.format(prob) + ")");
			
			if(rand < prob)
			{
				log.debug("=> accepted (consequence " + zone.getConsequence() + ")");
				con = zone.getConsequence();
			}
			else
			{
				log.debug("=> rejected");
			}
		}

		return con;
	}
	
	/**
	 * Choose all valid candidate shots based on the location of the ball
	 * and matched against the zones for each shot
	 * 
	 * @param pt
	 * @param ang
	 * @param elv
	 * @param pow
	 * @return
	 */
	private List<Shot> getCandidateShots(Point3D pt, double ang, double elv, double pow)
	{
		List<Shot> candidates = new ArrayList<Shot>();
		
		log.debug("which shots contain matching zones?");
		for(Shot shot : shots.getShotList())
		{
			if(shot.isValid(pt.getX(), pt.getZ(), ang, elv, pow))
			{
				log.debug("=> " + shot.getName());
				candidates.add(shot);
			}
			else
			{
				log.debug("xx " + shot.getName());
			}
		}
		
		if(candidates.size() == 0)
		{
			candidates.add(shots.getNullShot());
			log.debug("no candidate shots available, using null-shot");
		}
		else
		{
			log.debug(candidates.size() + " candidate shot(s) available");
		}
		
		return candidates;
	}
	
	public ShotModel analyse(List<Point2D> list)
	{
		Point2D[] points = new Point2D[list.size()];
		points = list.toArray(points);
		return analyse(points);
	}
	
	public ShotModel analyse(Point2D[] points)
	{
		int w = world.getWidth();
		int h = world.getHeight();
		
		ShotModel model = new ShotModel();

		if(points.length < 5)
			return null;
		
		Point2D p1 = points[0];
		Point2D p2 = points[points.length / 2];
		Point2D p3 = points[points.length - 1];

		double angle 	 = Calculator.angle(p2.getX() - p1.getX(), p2.getY() - p1.getY());
		double elevation = Calculator.angle(p3.getX() - p2.getX(), p3.getY() - p2.getY());
		double diff 	 = Math.abs(angle - elevation);

		double dx = p3.getX() - p1.getX();
		double dy = p3.getY() - p1.getY();
		double distance = sqrt(dx*dx + dy*dy);
		double diagonal = sqrt(w*w + h*h);
		
		model.setDesiredAngle(angle);
		model.setDesiredElevation(diff);
		model.setDesiredPower(distance * 2 / diagonal);
		
		log.debug(String.format("Shot analysis: angle=%.1f elev=%.1f power=%.1f", model.getDesiredAngle(), model.getDesiredElevation(), model.getDesiredPower()));
		
		return model;
	}
}
