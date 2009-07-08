package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;


public abstract class AbstractBasicConsequence implements Consequence
{
	public abstract WicketType getWicketType();
	
	public boolean isHit()
	{
		return false;
	}
	
	public boolean isHitBody()
	{
		return false;
	}
	
	public boolean isWicket()
	{
		return getWicketType() != WicketType.None;
	}
	
	public void perform()
	{
	}
	
	public String toString()
	{
		return this.getClass().getSimpleName();
	}
}