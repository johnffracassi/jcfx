package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Time;

public interface BowlerController extends PlayerController
{
	void setState(Player player, BowlerState bowlerState, Time time);
	void setBowler(Player player, BowlerRole role);
	Player getBowler(BowlerRole role);
	BowlingSide getBowlingSide();
}