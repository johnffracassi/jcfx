package com.siebentag.cj.game.action;

import com.siebentag.cj.mvc.FielderModel;
import com.siebentag.cj.mvc.FielderState;
import com.siebentag.cj.queue.Scope;

public class FielderStateChangeAction extends AbstractAction 
{
	private FielderModel fielder;
	private FielderState fielderState;
	
	public FielderStateChangeAction()
	{
	}
	
	public void run() 
	{
		fielder.setFielderState(fielderState, getTime());
	}

	@Override public Scope getScope() 
	{
		return Scope.Ball;
	}

	public FielderState getFielderState()
    {
    	return fielderState;
    }

	public void setFielderState(FielderState fielderState)
    {
    	this.fielderState = fielderState;
    }

	public FielderModel getFielder()
    {
    	return fielder;
    }

	public void setFielder(FielderModel fielder)
    {
    	this.fielder = fielder;
    }
}
