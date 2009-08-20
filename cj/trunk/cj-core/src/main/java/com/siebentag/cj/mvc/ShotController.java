package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

public interface ShotController
{
	public ShotResult playShot(Player batsman, Point3D initialLoc, Time time);
}
