/**
 * 
 */
package com.siebentag.cj.game.shot;

import com.siebentag.cj.util.math.TrajectoryModel;

/**
 * @author jeff
 *
 */
public class ShotModel extends TrajectoryModel
{
	private double desiredAngle;
	private double desiredElevation;
	private double desiredPower;
	
	private Consequence consequence;
	private double power;
	
	private Shot shot;

	public TrajectoryModel getTrajectory()
	{
		return new TrajectoryModel(getVelocity(), getAngle(), getElevation());
	}
	
	/**
     * @return the actualPower
     */
    public double getPower()
    {
    	return power;
    }

	/**
     * @param power the actualPower to set
     */
    public void setPower(double power)
    {
    	this.power = power;
    }

	/**
     * @return the desiredAngle
     */
    public double getDesiredAngle()
    {
    	return desiredAngle;
    }

	/**
     * @param desiredAngle the desiredAngle to set
     */
    public void setDesiredAngle(double desiredAngle)
    {
    	this.desiredAngle = desiredAngle;
    	setAngle(desiredAngle);
    }

	/**
     * @return the desiredElevation
     */
    public double getDesiredElevation()
    {
    	return desiredElevation;
    }

    private double fixElev(double elev)
    {
    	if(elev > 180.0)
    	{
    		return -(360.0 - elev);
    	}
    	else
    	{
    		return elev;
    	}
    }
    
	/**
     * @param ang the desiredElevation to set
     */
    public void setDesiredElevation(double ang)
    {
    	this.desiredElevation = fixElev(ang);
    	setElevation(fixElev(ang));
    }

	/**
     * @return the desiredPower
     */
    public double getDesiredPower()
    {
    	return desiredPower;
    }

	/**
     * @param desiredPower the desiredPower to set
     */
    public void setDesiredPower(double desiredPower)
    {
    	this.desiredPower = desiredPower;
    	this.power = desiredPower;
    }

	/**
     * @return the shot
     */
    public Shot getShot()
    {
    	return shot;
    }

	/**
     * @param shot the shot to set
     */
    public void setShot(Shot shot)
    {
    	this.shot = shot;
    }

	public Consequence getConsequence()
	{
		return consequence;
	}

	public void setConsequence(Consequence consequence)
	{
		this.consequence = consequence;
	}
}
