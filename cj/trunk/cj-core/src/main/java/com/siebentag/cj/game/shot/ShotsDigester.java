package com.siebentag.cj.game.shot;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import com.siebentag.cj.Config;

public class ShotsDigester
{
	private static final Logger log = Logger.getLogger("Shots");

	Shots shots;
	Digester digester = new Digester();
	
	public static void main(String[] args)
	{
		ShotsDigester sd = new ShotsDigester();
		
		try
		{
			sd.loadShots();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public ShotsDigester()
	{
	}
	
	private Shot loadShot(File file) throws IOException, SAXException
	{
		try
		{
			log.debug("loading shot from " + file);
			
			digester = new Digester();
			digester.setValidating(false);
	
			digester.addObjectCreate("shot", Shot.class);
			digester.addSetProperties("shot");	
			addZonesParser("shot");
			
			Shot shot = (Shot)digester.parse(file);
			
			log.debug("loaded " + shot.getZones().getZonesList().size() + " zones for shot " + shot.getName());
			
			return shot;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	private Zones loadZones() throws IOException, SAXException
	{
		File file = new ClassPathResource("/shots/zones.xml").getFile();

		log.debug("loading zones from " + file);

		digester = new Digester();
		digester.setValidating(false);

		digester.addObjectCreate("zones", Zones.class);
		addZoneParser("zones");

		Zones zones = (Zones)digester.parse(file);
		
		log.debug("loaded " + zones.getZonesList().size() + " zones");
		
		return zones;
	}
	
	public Shots loadShots()
	{
		try
		{
			Shots shots = new Shots();
			
			shots.setZones(loadZones());
			
			File dir = new ClassPathResource("/shots/zones.xml").getFile().getParentFile();
			for(File file : dir.listFiles(new FilenameFilter() { public boolean accept(File path, String filename) { return filename.endsWith(".shot.xml"); }}))
			{
				shots.addShot(loadShot(file));
			}
			
			resolve(shots);
			
			this.shots = shots;
			
			return shots;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	private void resolve(Shots shots)
	{
		Zones stdZones = shots.getZones();
		
		for(Shot shot : shots.getShotList())
		{
			for(Zone zone : shot.getZones().getZonesList())
			{
				if(zone.getId() != null)
				{
					zone.copyFrom(stdZones.getZone(zone.getId()));
				}
			}
		}
	}
	
	private void addZonesParser(String prefix)
	{
		digester.addObjectCreate(prefix + "/zones", Zones.class);
		addZoneParser(prefix + "/zones");
		digester.addSetNext(prefix + "/zones", "setZones");
	}
	
	private void addZoneParser(String prefix)
	{
		digester.addObjectCreate(prefix + "/zone", Zone.class);			
		digester.addSetProperties(prefix + "/zone");	
		addConsequenceParser(prefix + "/zone");
		addProbParser(prefix + "/zone");
		addShapeParser(prefix + "/zone");
		digester.addSetNext(prefix + "/zone/shape", "setShape");			
		digester.addSetNext(prefix + "/zone/consequence", "setConsequence");			
		digester.addSetNext(prefix + "/zone/probability", "setProbability");			
		digester.addSetNext(prefix + "/zone", "addZone");
	}
	
	private void addShapeParser(String prefix)
	{
		digester.addObjectCreate(prefix + "/shape", Shape.class);
		digester.addCallMethod(prefix + "/shape/point", "addPoint", 0);
	}
	
	private void addProbParser(String prefix)
	{
		digester.addObjectCreate(prefix + "/probability", ParamObject.class);
		digester.addSetProperties(prefix + "/probability");	
		digester.addObjectCreate(prefix + "/probability/param", Param.class);
		digester.addSetProperties(prefix + "/probability/param");	
		digester.addSetNext(prefix + "/probability/param", "addParam");
	}
	
	private void addConsequenceParser(String prefix)
	{
		digester.addObjectCreate(prefix + "/consequence", ParamObject.class);
		digester.addSetProperties(prefix + "/consequence");	
		digester.addObjectCreate(prefix + "/consequence/param", Param.class);
		digester.addSetProperties(prefix + "/consequence/param");	
		digester.addSetNext(prefix + "/consequence/param", "addParam");
	}
	
	public Shots getShots()
	{
		return shots;
	}
}