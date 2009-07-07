package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.util.math.Point3D;

public class PersonModel
{
	Player player;
	SceneObject object;
	Point3D baseLocation;
	String speechText;
	String label;

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public SceneObject getObject()
	{
		return object;
	}

	public void setObject(SceneObject object)
	{
		this.object = object;
	}

	public Point3D getBaseLocation()
	{
		return baseLocation;
	}

	public void setBaseLocation(Point3D baseLocation)
	{
		this.baseLocation = baseLocation;
	}

	public String getSpeechText()
	{
		return speechText;
	}

	public void setSpeechText(String speechText)
	{
		this.speechText = speechText;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}
}
