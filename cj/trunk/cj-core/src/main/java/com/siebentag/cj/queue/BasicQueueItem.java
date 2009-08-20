package com.siebentag.cj.queue;

import com.siebentag.cj.util.math.Time;

public abstract class BasicQueueItem implements QueueItem
{
	private Time time = Time.ZERO;
	private Priority priority = Priority.Medium;
	private Scope scope = Scope.Ball;
	
	public int compareTo(QueueItem o2) 
	{
		if(getTime().getTime() > o2.getTime().getTime()) return AFTER;
		if(getTime().getTime() < o2.getTime().getTime()) return BEFORE;
		
		if(getClassPriority() > o2.getClassPriority()) return BEFORE;
		if(getClassPriority() < o2.getClassPriority()) return AFTER;
		
		return getPriority().compareTo(o2.getPriority());
	}
	
	public Time getTime() 
	{
		return time;
	}
	
	public void setTime(Time time) 
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
