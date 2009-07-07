package com.siebentag.cj.game.shot;

public class EdgeToSlips extends AbstractEdge
{
	public EdgeToSlips() {}
	
	@Override public double getMaxAngle()
	{
		return 170.0;
	}

	@Override public double getMinAngle()
	{
		return 130.0;
	}
}