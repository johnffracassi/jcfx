package com.siebentag.cj.game;

public enum WicketType
{
	Bowled("Bowled", "b", true), 
	Caught("Caught", "c", true), 
	Stumped("Stumped", "st", true), 
	RunOut("Run Out", "RO", false), 
	LBW("LBW", "LBW", true),
	HitWicket("Hit Wicket", "HW", true), 
	None("", "", false);
	
	String shortDescription;
	String longDescription;
	boolean countsForBowler;
	
	WicketType(String longDescription, String shortDescription, boolean countsForBowler)
	{
		this.longDescription = longDescription;
		this.shortDescription = shortDescription;
		this.countsForBowler = countsForBowler;
	}
	
	public String getShortDescription()
	{
		return shortDescription;
	}
	
	public String getDescription()
	{
		return longDescription;
	}
	
	public boolean countsForBowler()
	{
		return countsForBowler;
	}
}