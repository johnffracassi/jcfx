package com.siebentag.cj.game.shot;

public class Consequences
{
	public static final Consequence BOWLED		= new Bowled();
	public static final Consequence EDGE_KEEPER = new EdgeToKeeper();
	public static final Consequence EDGE_SLIPS 	= new EdgeToSlips();
	public static final Consequence LBW 		= new LBWAppeal();
	public static final Consequence PLAYED_ON 	= new PlayedOn();
	public static final Consequence HIT 		= new Hit();
	public static final Consequence NONE		= new NoConsequence();
}
