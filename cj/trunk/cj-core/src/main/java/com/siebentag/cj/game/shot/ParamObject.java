package com.siebentag.cj.game.shot;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ParamObject
{
	private static final Logger log = Logger.getLogger("Shots");

	String type;
	Map<String,String> params;
	
	public ParamObject()
	{
		log.debug("Create new ParamObject");
		params = new HashMap<String,String>();
	}
	
	public String toString()
	{
		return "[" + type + "]";
	}
	
	public void setType(String type)
	{
		log.debug("Set type of ParamObject to " + type);
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public Map<String,String> getParams()
	{
		return params;
	}
	
	public void addParam(Param param)
	{
		log.debug("add value to param " + param);
		params.put(param.getName(), param.getValue());
	}
}
