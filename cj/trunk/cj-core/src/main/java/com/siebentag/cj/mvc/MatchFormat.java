package com.siebentag.cj.mvc;

public class MatchFormat
{
	private String name = "One Day International";
	private String shortName = "ODI";

	private boolean limitedOvers = true;
	private int ballsPerOver = 6;
	private int runsForNoball = 1;
	private boolean rebowlNoballs = true;
	private int runsForWide = 1;
	private boolean rebowlWides = true;

	private int maxOversPerInnings = 50;
	private int maxInningsPerTeam = 2;

	private int minOversPerDay = 100;
	private int maxOversPerDay = 100;
	private int maxNumberOfDays = 1;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public boolean isLimitedOvers()
	{
		return limitedOvers;
	}

	public void setLimitedOvers(boolean limitedOvers)
	{
		this.limitedOvers = limitedOvers;
	}

	public int getMaxOversPerInnings()
	{
		return maxOversPerInnings;
	}

	public void setMaxOversPerInnings(int maxOversPerInnings)
	{
		this.maxOversPerInnings = maxOversPerInnings;
	}

	public int getMaxInningsPerTeam()
	{
		return maxInningsPerTeam;
	}

	public void setMaxInningsPerTeam(int maxInningsPerTeam)
	{
		this.maxInningsPerTeam = maxInningsPerTeam;
	}

	public int getMinOversPerDay()
	{
		return minOversPerDay;
	}

	public void setMinOversPerDay(int minOversPerDay)
	{
		this.minOversPerDay = minOversPerDay;
	}

	public int getMaxOversPerDay()
	{
		return maxOversPerDay;
	}

	public void setMaxOversPerDay(int maxOversPerDay)
	{
		this.maxOversPerDay = maxOversPerDay;
	}

	public int getMaxNumberOfDays()
	{
		return maxNumberOfDays;
	}

	public void setMaxNumberOfDays(int maxNumberOfDays)
	{
		this.maxNumberOfDays = maxNumberOfDays;
	}

	public int getBallsPerOver()
    {
    	return ballsPerOver;
    }

	public void setBallsPerOver(int ballsPerOver)
    {
    	this.ballsPerOver = ballsPerOver;
    }

	public int getRunsForNoball()
    {
    	return runsForNoball;
    }

	public void setRunsForNoball(int runsForNoball)
    {
    	this.runsForNoball = runsForNoball;
    }

	public boolean isRebowlNoballs()
    {
    	return rebowlNoballs;
    }

	public void setRebowlNoballs(boolean rebowlNoballs)
    {
    	this.rebowlNoballs = rebowlNoballs;
    }

	public int getRunsForWide()
    {
    	return runsForWide;
    }

	public void setRunsForWide(int runsForWide)
    {
    	this.runsForWide = runsForWide;
    }

	public boolean isRebowlWides()
    {
    	return rebowlWides;
    }

	public void setRebowlWides(boolean rebowlWides)
    {
    	this.rebowlWides = rebowlWides;
    }

}
