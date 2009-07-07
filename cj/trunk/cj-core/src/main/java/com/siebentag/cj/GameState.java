package com.siebentag.cj;

public enum GameState 
{
	Idle, 

	BowlingRunUp, BowlingAction, BowlingDelivery, BowlingDelivered,
	PlayingShot, 
	Fielded,
	
	ShotPlayed, Hit, Missed, ThroughToKeeper, ThrownToKeeper, ThrownToBowler,
	CrossedBoundary
}
