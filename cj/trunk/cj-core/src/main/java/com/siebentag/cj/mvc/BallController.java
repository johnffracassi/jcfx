package com.siebentag.cj.mvc;

import java.awt.Graphics2D;

import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryPath;

public interface BallController
{
	void paint(Graphics2D g, double time);
	Point3D getLocation(double time);
	TrajectoryPath getTrajectoryPath();
	void setTrajectoryPath(TrajectoryPath path, double time);
	void setBallState(BallState state);
	BallState getBallState();
}
