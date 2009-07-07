package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public class HitBatsman extends AbstractBasicConsequence
{
	public HitBatsman()
	{
	}
	
	public WicketType getWicketType()
	{
		return WicketType.None;
	}

	public boolean isHitBody()
	{
		return true;
	}
	
	public boolean isHit()
	{
		return false;
	}

	public boolean isWicket()
	{
		return false;
	}
}
