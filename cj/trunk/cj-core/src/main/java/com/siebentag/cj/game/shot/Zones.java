package com.siebentag.cj.game.shot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

public class Zones
{
	private static final Logger log = Logger.getLogger("Shots");

	List<Zone> zones;
	
	public Zones()
	{
		log.debug("Create new Zones");
		zones = new ArrayList<Zone>();
	}
	
	public void addZone(Zone zone)
	{
		log.debug("Zone.addZone(" + zone.getId() + " / " + zone.getName() + ")");
		zones.add(zone);
	}
	
	public List<Zone> getZonesList()
	{
		sort();
		return zones;
	}
	
	public void sort()
	{
		Collections.sort(zones, new Comparator<Zone>() {
			public int compare(Zone z1, Zone z2) {
				if(z1.getPriority() > z2.getPriority()) return -1;
				if(z2.getPriority() > z1.getPriority()) return 1;
				return 0;
			}
		});
	}
	
	public List<Zone> getZones(double x, double y)
	{
		List<Zone> zoneList = new ArrayList<Zone>();
		
		for(Zone zone : getZonesList())
		{
			if(!zone.isDefault() && zone.getShape().contains(x, y))
			{
				zoneList.add(zone);
//				log.debug(zone + " contains " + Formatter.point(x, y));
			}
		}
		
		return zoneList;
	}
	public Zone getZone(String id)
	{
		for(Zone zone : getZonesList())
		{
			if(zone.getId().equalsIgnoreCase(id))
			{
				return zone;
			}
		}
		
		return null;
	}
}
