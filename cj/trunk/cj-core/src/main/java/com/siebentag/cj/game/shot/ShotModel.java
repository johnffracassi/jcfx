/**
 * 
 */
package com.siebentag.cj.game.shot;

import com.siebentag.cj.util.math.Angle;
import com.siebentag.cj.util.math.TrajectoryModel;

/**
 * @author jeff
 *
 */
public class ShotModel extends TrajectoryModel
{
	private Angle desiredAngle;
	private Angle desiredElevation;
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
    public Angle getDesiredAngle()
    {
    	return desiredAngle;
    }

	/**
     * @param desiredAngle the desiredAngle to set
     */
    public void setDesiredAngle(Angle desiredAngle)
    {
    	this.desiredAngle = desiredAngle;
    	setAngle(desiredAngle);
    }

	/**
     * @return the desiredElevation
     */
    public Angle getDesiredElevation()
    {
    	return desiredElevation;
    }

    private Angle fixElev(Angle elevation)
    {
    	double elevDegrees = elevation.degrees();
    	
    	if(elevDegrees > 180.0)
    	{
    		return Angle.degrees(-(360.0 - elevDegrees));
    	}
    	else
    	{
    		return Angle.degrees(elevDegrees);
    	}
    }
    
	/**
     * @param ang the desiredElevation to set
     */
    public void setDesiredElevation(Angle ang)
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
