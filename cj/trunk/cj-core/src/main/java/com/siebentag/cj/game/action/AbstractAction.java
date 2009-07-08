package com.siebentag.cj.game.action;

import com.siebentag.cj.queue.BasicQueueItem;

public abstract class AbstractAction extends BasicQueueItem implements Action
{
	ActionState state = ActionState.Idle;

	public abstract void run();
	
	public ActionState getState() 
	{
		return state;
	}

	public void setState(ActionState state) 
	{
		this.state = state;
	}
	
	@Override public final int getClassPriority() 
	{
		return 1;
	}
	
	public void cancel() 
	{
		setState(ActionState.Cancelled);
	}
	
	@Override public boolean isCancelled()
	{
		return getState() == ActionState.Cancelled;
	}
	
	public String getDescription()
	{
	    return "[class=" + getClass().getName() + " / time=" + getTime() + "]";
	}
}