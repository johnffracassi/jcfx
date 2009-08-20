package com.siebentag.cj.mvc;

import java.awt.Color;
import java.awt.Graphics2D;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

public interface PersonController
{
	void paint(Graphics2D g, Time time);
	void setColours(Color main, Color secondary);
	
	Point3D getLocation(Player person, Time time);
	void setLocation(Player person, Point3D loc);
	void doMove(PersonMovement movement);
	void speak(Player person, String text);
	
	void resetForBall();
}
