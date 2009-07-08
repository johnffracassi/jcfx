package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;

public interface BowlerController extends PlayerController
{
	void setState(Player player, BowlerState bowlerState, double time);
	void setBowler(Player player, BowlerRole role);
	Player getBowler(BowlerRole role);
	BowlingSide getBowlingSide();
}