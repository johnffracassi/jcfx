package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public class NoConsequence extends AbstractBasicConsequence
{
	@Override public WicketType getWicketType()
	{
		return WicketType.None;
	}
	
	public void perform()
	{
		// allow to pass through to keeper
	}
}
