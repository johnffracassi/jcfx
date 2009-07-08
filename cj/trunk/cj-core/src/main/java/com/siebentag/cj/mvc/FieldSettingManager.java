package com.siebentag.cj.mvc;

import org.springframework.stereotype.Component;

import com.siebentag.cj.game.field.FieldPosition;

@Component
public class FieldSettingManager
{
	FieldPosition[] f1 = new FieldPosition[] {
		FieldPosition.BowlOverFastRunUp,
		FieldPosition.KeeperFast,
		FieldPosition.FieldCover,
		FieldPosition.FieldFineLeg,
		FieldPosition.FieldFirstSlip,
		FieldPosition.FieldMidOff,
		FieldPosition.FieldMidOn,
		FieldPosition.FieldMidWicket,
		FieldPosition.FieldPoint,
		FieldPosition.FieldSquareLeg,
		FieldPosition.FieldThirdMan
	};
	
	public FieldPosition[] getFieldSetting(String key)
	{
		return f1;
	}
}
