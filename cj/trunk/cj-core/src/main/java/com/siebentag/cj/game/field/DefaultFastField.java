package com.siebentag.cj.game.field;

import java.util.ArrayList;
import java.util.List;

public class DefaultFastField extends FieldSetting
{
	List<FieldPosition> positions;
	
	public DefaultFastField()
	{
		setName("Default Fast"); 
		
		positions = new ArrayList<FieldPosition>(9);
		
		positions.add(FieldPosition.FieldCover);
		positions.add(FieldPosition.FieldFirstSlip);
		positions.add(FieldPosition.FieldMidOff);
		positions.add(FieldPosition.FieldMidOn); 
		positions.add(FieldPosition.FieldMidWicket);
		positions.add(FieldPosition.FieldPoint);
		positions.add(FieldPosition.FieldSecondSlip);
		positions.add(FieldPosition.FieldThirdSlip);
		positions.add(FieldPosition.FieldFineLeg);
	}
	
	@Override public List<FieldPosition> getPositions()
	{
		return positions;
	}
}