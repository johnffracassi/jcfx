package com.siebentag.cj.game.shot;

import java.util.List;

import org.apache.log4j.Logger;

import com.siebentag.cj.util.math.Angle;

public class Shot 
{
	private static final Logger log = Logger.getLogger("Shots");

	private String name;
	private Zones zones;
	private Angle angMax 	= Angle.MAX;
	private Angle angMin 	= Angle.ZERO;
	private Angle elevMax 	= Angle.degrees(75);
	private Angle elevMin 	= Angle.degrees(-75);
	private double powMax 	=   3.0;
	private double powMin 	=   0.0;

	public Shot() 
	{
		log.debug("Create new Shot");
	}

	public void setZones(Zones zones) 
	{
		log.debug("Shot.setZones(...)");
		this.zones = zones;
	}

	public void setName(String name) 
	{
		log.debug("Shot.setName(" + name + ")");
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	public Zones getZones() 
	{
		return zones;
	}

	public List<Zone> getZones(double x, double y)
	{
		return zones.getZones(x, y);
	}
	
	public boolean isValid(double x, double y, Angle angle, Angle elev, double power)
	{
		if("no shot".equalsIgnoreCase(name))
		{
			return false;
		}
		
		log.debug("checking if " + this + " contains valid angle/power/elev/zones?");
		
		if(!isValidAngle(angle, getAngMin(), getAngMax()))
		{
			log.debug("  rej: not valid angle");
			return false;
		}
		
		// TODO fix shot elevation testing
//		if(!isValidAngle(elev, getElevMin(), getElevMax()))
//		{
//			log.debug("  rej: not valid elev");
//			return false;
//		}
		
		if(!isValidPower(power, getPowMin(), getPowMax()))
		{
			log.debug("  rej: not valid power");
			return false;
		}
		
		if(getZones(x, y).size() > 0)
		{
			log.debug("  acc: valid zones");
			return true;
		}
		
		log.debug("  rej: no valid zones");
		return false;
	}
	
	private boolean isValidPower(double power, double min, double max)
	{
		return min < power && power < max;
	}
	
	private boolean isValidAngle(Angle angle, Angle min, Angle max)
	{
		if(min.degrees() < max.degrees())
		{
			if(min.radians() < angle.radians() && angle.radians() < max.radians())
			{
				return true;
			}
		}
		else
		{
			return isValidAngle(angle, min, Angle.MAX) || isValidAngle(angle, Angle.ZERO, max);
		}
		
		return false;
	}
	
	@Override public String toString() 
	{
		return getName();
	}

	public Angle getAngMax()
	{
		return angMax;
	}

	public void setAngMax(double degrees) {
		angMax = (Angle.degrees(degrees));
	}


	public Angle getAngMin()
	{
		return angMin;
	}

	public void setAngMin(double degrees) {
		angMin = (Angle.degrees(degrees));
	}

	public double getPowMax()
	{
		return powMax;
	}

	public void setPowMax(double powMax)
	{
		this.powMax = powMax;
	}

	public double getPowMin()
	{
		return powMin;
	}

	public void setPowMin(double powMin)
	{
		this.powMin = powMin;
	}

	public Angle getElevMax()
	{
		return elevMax;
	}

	public void setElevMax(double degrees) {
		elevMax = (Angle.degrees(degrees));
	}

	public Angle getElevMin()
	{
		return elevMin;
	}

	public void setElevMin(double degrees) {
		elevMin = (Angle.degrees(degrees));
	}
}
