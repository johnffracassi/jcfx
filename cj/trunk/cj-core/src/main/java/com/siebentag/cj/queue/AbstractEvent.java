package com.siebentag.cj.queue;

import com.siebentag.cj.game.action.Action;


public abstract class AbstractEvent extends BasicQueueItem implements Event
{
	private boolean active = true;
	private Action parent;
	
	public void cancel()
	{
		active = false;
	}

	public Action getParent() 
	{
		return parent;
	}

	public void setParent(Action parent) 
	{
		this.parent = parent;
	}

	public final int getClassPriority() 
	{
		return 2;
	}
	
	@Override public boolean isCancelled() 
	{
		if(parent != null)
		{
			return parent.isCancelled();
		}
		
		return !active;
	}
	
	public String toString()
	{
		return getDescription();
	}
}
