package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public class Bowled extends AbstractBasicConsequence
{
	public Bowled()
	{
	}
	
	public WicketType getWicketType()
	{
		return WicketType.Bowled;
	}
}
