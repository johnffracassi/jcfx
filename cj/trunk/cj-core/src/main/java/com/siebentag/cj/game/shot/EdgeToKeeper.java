package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public class EdgeToKeeper extends AbstractBasicConsequence
{
	public EdgeToKeeper() {}

	@Override public boolean isHit()
	{
		return false;
	}

	@Override public WicketType getWicketType()
	{
		return WicketType.Caught;
	}
}

