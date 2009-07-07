package com.siebentag.cj.game.shot;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Shots
{
	private static final Logger log = Logger.getLogger("Shots");

	Zones zones;
	List<Shot> shots;
	
	public Shots()
	{
		log.debug("Create new Shots collection");
		shots = new ArrayList<Shot>();
	}
	
	public Shot getNullShot()
	{
		for(Shot shot : shots)
		{
			if(shot.getName().equalsIgnoreCase("No Shot"))
			{
				return shot;
			}
		}
		
		// TODO throw exception or something?
		log.error("could not find null-shot");
		return null;
	}
	
	public void addShot(Shot shot)
	{
		log.debug("Shots.addShot(" + shot + ")");
		shots.add(shot);
	}
	
	public void setZones(Zones zones)
	{
		log.debug("Shots.setZones(...)");
		this.zones = zones;
	}
	
	public Zones getZones()
	{
		return zones;
	}
	
	public List<Shot> getShotList()
	{
		return shots;
	}
}
