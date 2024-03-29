package com.siebentag.cj.mvc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.Manager;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

public abstract class PlayerControllerImpl implements PlayerController
{
	private static final Logger log = Logger.getLogger(PlayerControllerImpl.class);

	private Map<Player,Point3D> playerLoc;
	private Map<Player,PersonMovement> movements;
	private Map<Player,String> speechMsg;
	private Map<Player,Time> stateChangeTime;

	@Autowired
	Manager manager;
	
	public PlayerControllerImpl()
	{
		stateChangeTime = new HashMap<Player,Time>();
		playerLoc = new HashMap<Player,Point3D>();
		movements = new HashMap<Player,PersonMovement>();
	}

	public abstract void paint(Graphics2D g, Time time);

	public Point3D getLocation(Player person, Time time)
	{
		Point3D loc = Point3D.ORIGIN;
		
		if(movements.containsKey(person))
		{
			PersonMovement movement = movements.get(person);
			loc = movement.getLocation(time);
		}
		else
		{
			loc = playerLoc.get(person);
		}
		
		return loc;
	}
	
	protected void setStateChangeTime(Player player, Time time)
	{
		stateChangeTime.put(player, time);
	}
	
	protected double getTimeSinceStateChange(Player player, Time time)
	{
		double changeTime = getStateChangeTime(player);
		return time.subtract(changeTime).getTime();
	}
	
	public double getStateChangeTime(Player player)
	{
		double time = 0.0;
		
		if(stateChangeTime.containsKey(player)) {
			time = stateChangeTime.get(player).getTime();
		} else {
			setStateChangeTime(player, Time.ZERO);
		}
		
		return time;
	}
	
	/**
	 * Set the location and cancel any movements as well
	 */
	public void setLocation(Player person, Point3D location) 
	{
		playerLoc.put(person, location);
		movements.remove(person);
		
		log.debug("set location of " + person + " to " + location);
	}
	
	public void doMove(PersonMovement movement)
	{
		log.info("Moving person: " + movement);
		movements.put(movement.getPerson(), movement);
	}
	
	public void setColours(Color main, Color secondary) 
	{
	}

	public void speak(Player person, String text) 
	{
		speechMsg.put(person, text);
	}
}
