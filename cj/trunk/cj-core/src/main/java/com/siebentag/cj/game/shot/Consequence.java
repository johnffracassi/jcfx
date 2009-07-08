package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public interface Consequence
{
	WicketType getWicketType();
	boolean isHit();
	boolean isWicket();
	boolean isHitBody();
	void perform();
}