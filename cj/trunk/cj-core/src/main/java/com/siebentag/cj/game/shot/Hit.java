package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.WicketType;

public class Hit extends AbstractBasicConsequence
{
	double power;
	double accuracy;

	public void perform()
	{
	}
	
	public double getPower()
	{
		return power;
	}

	public void setPower(double power)
	{
		this.power = power;
	}

	public void setPower(String power)
	{
		this.power = Double.parseDouble(power);
	}

	public double getAccuracy()
	{
		return accuracy;
	}

	public void setAccuracy(String accuracy)
	{
		this.accuracy = Double.parseDouble(accuracy);
	}

	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}

	public WicketType getWicketType()
	{
		return WicketType.None;
	}

	public boolean isHit()
	{
		return true;
	}

	public boolean isWicket()
	{
		return false;
	}
}
