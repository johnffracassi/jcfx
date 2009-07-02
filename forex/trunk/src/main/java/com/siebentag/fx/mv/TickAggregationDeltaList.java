package com.siebentag.fx.mv;

import java.util.ArrayList;

import com.siebentag.fx.strategy.PriceList;

public class TickAggregationDeltaList extends ArrayList<TickAggregationRow>
{
	private PriceList shortList = new PriceList("short");
	private PriceList longList = new PriceList("long");
	
	public boolean add(TickAggregationRow delta)
	{
		shortList.addPrice(String.valueOf(delta.getStartDate()), delta.getProfitShort());
		longList.addPrice(String.valueOf(delta.getStartDate()), delta.getProfitLong());
		return super.add(delta);
	}

	public PriceList getShortList()
	{
		return shortList;
	}

	public void setShortList(PriceList shortList)
	{
		this.shortList = shortList;
	}

	public PriceList getLongList()
	{
		return longList;
	}

	public void setLongList(PriceList longList)
	{
		this.longList = longList;
	}
}
