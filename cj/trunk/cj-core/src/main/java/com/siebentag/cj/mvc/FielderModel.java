package com.siebentag.cj.mvc;

import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.model.Player;

public class FielderModel extends PersonModel
{
	private FielderState fielderState;
	private FieldPosition fieldPosition;
	private double timeOfLastStateChange;
	private boolean isFielding;

	public FielderModel() 
	{
	}
	
	public FielderModel(String name, FieldPosition pos)
	{
		super();

		Player player = new Player();
		player.setFirstName(name);
		player.setKey(name);
		player.setSurname(name);
		setPlayer(player);
		
		setBaseLocation(pos.getLocation());
		setLabel(name);
	}
	
	@Override
	public String toString() {
		return player.getKey();
	}
	
	public FielderState getFielderState()
	{
		return fielderState;
	}

	public void setFielderState(FielderState fielderState, double time)
	{
		this.fielderState = fielderState;
		this.timeOfLastStateChange = time;
	}

	public FieldPosition getFieldPosition()
	{
		return fieldPosition;
	}

	public void setFieldPosition(FieldPosition fieldPosition)
	{
		this.fieldPosition = fieldPosition;
	}

	public double getTimeOfLastStateChange()
    {
    	return timeOfLastStateChange;
    }

	public boolean isFielding()
    {
    	return isFielding;
    }

	public void setFielding(boolean isFielding)
    {
    	this.isFielding = isFielding;
    }
}