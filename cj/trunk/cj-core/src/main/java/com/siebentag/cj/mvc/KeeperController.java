package com.siebentag.cj.mvc;

import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.TrajectoryPath;

public interface KeeperController extends PlayerController
{
	void setState(FielderState keeperState, double time);
	void assignKeeper(Player player);
	Player getKeeper();
	void moveToStumps(double time);
	void moveToLineOfBall(TrajectoryPath path, double time);
	public FieldPosition getFieldPosition();
	public void setFieldPosition(FieldPosition fieldPosition);
}