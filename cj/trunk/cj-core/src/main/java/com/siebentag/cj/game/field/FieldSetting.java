package com.siebentag.cj.game.field;

import java.util.List;

public abstract class FieldSetting
{
	private String			name	= "*Unnamed*";
	private FieldPosition	keeper;
	private FieldPosition	bowler;

	public abstract List<FieldPosition> getPositions();

	public FieldPosition getPosition(int idx)
	{
		return getPositions().get(idx);
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public FieldPosition getKeeper()
	{
		return keeper;
	}

	public void setKeeper(FieldPosition keeper)
	{
		this.keeper = keeper;
	}

	public FieldPosition getBowler()
	{
		return bowler;
	}

	public void setBowler(FieldPosition bowler)
	{
		this.bowler = bowler;
	}
}
