package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Team;

public interface PlayerController extends PersonController
{
	void resetForInnings(Team battingTeam, Team bowlingTeam);
}