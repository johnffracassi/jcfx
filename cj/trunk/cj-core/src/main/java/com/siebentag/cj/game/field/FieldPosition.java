package com.siebentag.cj.game.field;

import com.siebentag.cj.util.math.Point3D;


public enum FieldPosition
{
	UmpireBowler("Umpire", 0.0, 13.0),
	UmpireSquareLeg("Square Leg Umpire", -19.0, -10.0),

	BowlOverFastRunUp("Run Up (O-Fast)", 3.0, 25.0),
	BowlOverSlowRunUp("Run Up (O-Spin)", 3.0, 13.0),
	BowlOverRelease("ReleasePoint (O)", 1.2, 9.0),
	BowlOverFollowThrough("Follow Through (O)", 2.8, 5.0),
	BowlAroundFastRunUp("Run Up (A-Fast)", -3.0, 25.0),
	BowlAroundSlowRunUp("Run Up (A-Spin)", -3.0, 13.0),
	BowlAroundRelease("ReleasePoint (A)", -1.2, 9.0),
	BowlAroundFollowThrough("Follow Through (A)", -2.8, 5.0),
	
	BatStrikerRight("Bat Striker RH", -0.6, -9.0),
	BatStrikerLeft("Bat Striker LH", 0.6, -9.0),
	BatNonStrikerOver("Bat Non-Striker Over", -1.3, 9.2),
	BatNonStrikerAround("Bat Non-Striker Around", 1.3, 9.2),
	
	BatRunStrikerOdd("BatRunStrikerOdd", 1.5, 9.0),
	BatRunStrikerEven("BatRunStrikerEven", 1.5, -9.0),
	BatRunNonStrikerOdd("BatRunNonStrikerOdd", -1.5, -9.0),
	BatRunNonStrikerEven("BatRunNonStrikerEven", -1.5, 9.0),
	
	KeeperFast("Wicket Keeper (Fast)", 0.0, -19.0), 
	KeeperSpin("Wicket Keeper (Spin)", 0.0, -11.0), 
	KeeperReturn("Keeper Return", 0.0, -10.4),
	
	FieldFirstSlip("1st Slip", 1.8, -19.5), 
	FieldSecondSlip("2nd Slip", 3.6, -18.5), 
	FieldThirdSlip("3nd Slip", 5.4, -17.5), 
	FieldMidOn("Mid-on", 16.0, 16.0), 
	FieldMidOff("Mid-off", -16.0, 16.0),
	FieldMidWicket("Mid Wicket", 24.0, 0.0),
	FieldCover("Cover", -24.0, 0.0),
	FieldSquareLeg("Square Leg", -20.0, -12.0), 
	FieldPoint("Point", 22.0, -15.0),
	FieldFineLeg("Fine Leg", -35.0, -50.0), 
	FieldThirdMan("3rd Man", 40.0, -50.0);
	
	
	Point3D location;
	String name;
	
	FieldPosition(String name, double x, double y)
	{
		this.location = new Point3D(name, x, y, 0.0);
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Point3D getLocation()
	{
		return location;
	}
}
