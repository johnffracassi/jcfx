package com.siebentag.cj.mvc;

import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TrajectoryPath;

public interface KeeperController extends PlayerController
{
	void setState(FielderState keeperState, Time time);
	void assignKeeper(Player player);
	Player getKeeper();
	void moveToStumps(Time time);
	void moveToLineOfBall(TrajectoryPath path, Time time);
	public FieldPosition getFieldPosition();
	public void setFieldPosition(FieldPosition fieldPosition);
}