package com.siebentag.cj.queue;

public abstract class BasicQueueItem implements QueueItem
{
	private double time = 0.0;
	private Priority priority = Priority.Medium;
	private Scope scope = Scope.Ball;
	
	public int compareTo(QueueItem o2) 
	{
		if(getTime() > o2.getTime()) return AFTER;
		if(getTime() < o2.getTime()) return BEFORE;
		
		if(getClassPriority() > o2.getClassPriority()) return BEFORE;
		if(getClassPriority() < o2.getClassPriority()) return AFTER;
		
		return getPriority().compareTo(o2.getPriority());
	}
	
	public double getTime() 
	{
		return time;
	}
	
	public void setTime(double time) 
	{
		this.time = time;
	}
	
	public Priority getPriority() 
	{
		return priority;
	}
	
	public void setPriority(Priority priority) 
	{
		this.priority = priority;
	}
	
	public Scope getScope() 
	{
		return scope;
	}
	
	public void setScope(Scope scope) 
	{
		this.scope = scope;
	}

	public int getClassPriority() 
	{
		return 1;
	}

	public boolean isCancelled() 
	{
		return false;
	}
}
