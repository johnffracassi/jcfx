package com.siebentag.cj.game.shot;

import org.apache.log4j.Logger;

public class Param 
{
	private static final Logger log = Logger.getLogger("Shots");

	private String name;
	private String value;

	public Param()
	{
		log.debug("Create new Param");
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		log.debug("setName(" + name + ")");
		this.name = name;
	}

	public String getValue() 
	{
		return value;
	}

	public void setValue(String value) 
	{
		log.debug("setValue(" + value + ")");
		this.value = value;
	}
}
