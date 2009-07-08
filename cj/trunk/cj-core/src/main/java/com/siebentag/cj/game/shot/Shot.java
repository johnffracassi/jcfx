package com.siebentag.cj.game.shot;

import java.util.List;

import org.apache.log4j.Logger;

public class Shot 
{
	private static final Logger log = Logger.getLogger("Shots");

	String name;
	Zones zones;
	private double angMax 	= 359.9999;
	private double angMin 	=   0.0001;
	private double powMax 	=   3.0;
	private double powMin 	=   0.0;
	private double elevMax 	=  75.0;
	private double elevMin 	= -75.0;

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
	
	public boolean isValid(double x, double y, double angle, double elev, double power)
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
	
	private boolean isValidAngle(double angleRadians, double minDegrees, double maxDegress)
	{
		if(minDegrees < maxDegress)
		{
			if(Math.toRadians(minDegrees) < angleRadians && angleRadians < Math.toRadians(maxDegress))
			{
				return true;
			}
		}
		else
		{
			return isValidAngle(angleRadians, minDegrees, 360.0) || isValidAngle(angleRadians, 0.0, maxDegress);
		}
		
		return false;
	}
	
	@Override public String toString() 
	{
		return getName();
	}

	public double getAngMax()
	{
		return angMax;
	}

	public void setAngMax(double angMax)
	{
		this.angMax = angMax;
	}

	public double getAngMin()
	{
		return angMin;
	}

	public void setAngMin(double angMin)
	{
		this.angMin = angMin;
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

	public double getElevMax()
	{
		return elevMax;
	}

	public void setElevMax(double elevMax)
	{
		this.elevMax = elevMax;
	}

	public double getElevMin()
	{
		return elevMin;
	}

	public void setElevMin(double elevMin)
	{
		this.elevMin = elevMin;
	}
}
