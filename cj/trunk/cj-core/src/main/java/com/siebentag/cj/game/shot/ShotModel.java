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
	private double actualAngle;
	private double actualElevation;
	private double actualPower;
	
	private Shot shot;

	public void calculate()
	{
		setAngle(getDesiredAngle());
		setElevation(getDesiredElevation());
//		setVelocity();
	}
	
	public TrajectoryModel getTrajectory()
	{
		return new TrajectoryModel(getVelocity(), getAngle(), getElevation());
	}
	
	/**
     * @return the actualAngle
     */
    public double getActualAngle()
    {
    	return actualAngle;
    }

	/**
     * @param actualAngle the actualAngle to set
     */
    public void setActualAngle(double actualAngle)
    {
    	this.actualAngle = actualAngle;
    }

	/**
     * @return the actualElevation
     */
    public double getActualElevation()
    {
    	return actualElevation;
    }

	/**
     * @param actualElevation the actualElevation to set
     */
    public void setActualElevation(double ang)
    {
    	this.actualElevation = fixElev(ang);
    }

	/**
     * @return the actualPower
     */
    public double getActualPower()
    {
    	return actualPower;
    }

	/**
     * @param actualPower the actualPower to set
     */
    public void setActualPower(double actualPower)
    {
    	this.actualPower = actualPower;
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
    	this.actualAngle = desiredAngle;
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
    	this.actualElevation = fixElev(ang);
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
    	this.actualPower = desiredPower;
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
