package com.siebentag.cj.game.shot;

import org.apache.log4j.Logger;

import com.siebentag.cj.game.math.prob.ProbabilityModel;

public class Zone
{
	private static final Logger log = Logger.getLogger("Shots");

	String id;
	String name;
	int priority;
	Shape shape;
	Consequence consequence;
	ProbabilityModel probability;

	@Override public String toString() 
	{
		return name + " - " + consequence + " (" + probability + ")";  
	}
	
	/**
     * @return the probability
     */
    public ProbabilityModel getProbability()
    {
    	return probability;
    }

    public boolean isDefault()
    {
    	return "default".equalsIgnoreCase(name);
    }
    
	/**
     * @param probability the probability to set
     */
    public void setProbability(ParamObject param)
    {
		log.debug("Zone.setProbability(" + probability + ")");
		this.probability = ParamObjectFactory.createProbability(param);
    }

    public void copyFrom(Zone zone)
    {
    	log.debug("copy from " + zone.getId() + " to " + name);
    	
    	if(name == null)
    		name = zone.getName();
    	
    	if(priority == 0)
    		priority = zone.getPriority();
    	
    	if(shape == null)
    		shape = zone.getShape();
    	
    	if(consequence == null)
    		consequence = zone.getConsequence();
    	
    	if(probability == null)
    		probability = zone.getProbability();
    }
    
	public Zone()
	{
		log.debug("Create new Zone");
	}
	
	public void setConsequence(ParamObject param)
	{
		log.debug("Zone.setConsequence(" + param.getType() + ")");
		this.consequence = ParamObjectFactory.createConsequence(param);
	}
	
	public Consequence getConsequence()
	{
		return consequence;
	}
	
	public void setPriority(int priority)
	{
		log.debug("Zone.setPriority(" + priority + ")");
		this.priority = priority;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public void setId(String id)
	{
		log.debug("Zone.setId(" + id + ")");
		this.id = id;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setName(String name)
	{
		log.debug("Zone.setName(" + name + ")");
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setShape(Shape shape)
	{
		log.debug("Zone.setShape(...)");
		this.shape = shape;
	}
	
	public Shape getShape()
	{
		return shape;
	}
}
