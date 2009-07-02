package com.jeff.fx.common;

import java.util.Date;

public interface FXDataPoint
{
	long getBuyVolume();
	long getSellVolume();
	String getInstrument();
	FXDataSource getDataSource();
	Date getDate();
}
