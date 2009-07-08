package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.FielderIntersection;
import com.siebentag.cj.util.math.TrajectoryPath;
import com.siebentag.cj.util.math.TrajectoryPoint;

public interface FielderController extends PlayerController
{
	FielderModel getFielder(Player player);
	FielderIntersection getClosestFielder(TrajectoryPoint point);
	FielderIntersection getClosestFielder(TrajectoryPath path);
	void fieldBall(ShotResult result, double time);
}
