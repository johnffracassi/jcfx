package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;

public interface UmpireController extends PlayerController
{
	void setState(Player person, UmpireState umpireState);
	void setUmpire(Player person, UmpireRole role);
	Player getUmpire(UmpireRole role);
}