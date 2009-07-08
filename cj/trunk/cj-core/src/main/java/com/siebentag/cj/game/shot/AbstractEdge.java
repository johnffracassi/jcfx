package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public abstract class AbstractEdge extends AbstractBasicConsequence
{
	abstract double getMinAngle();
	abstract double getMaxAngle();
	
	public WicketType getWicketType()
	{
		return WicketType.Caught;
	}
	
	public boolean isHit()
	{
		return true;
	}
}