package com.siebentag.fx;

import java.util.Date;

import com.siebentag.fx.source.FXDataSource;

public interface FXDataPoint
{
	long getBuyVolume();
	long getSellVolume();
	String getInstrument();
	FXDataSource getDataSource();
	Date getDate();
}
