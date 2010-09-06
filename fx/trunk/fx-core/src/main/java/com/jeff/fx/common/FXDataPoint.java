package com.jeff.fx.common;

import org.joda.time.LocalDateTime;

public interface FXDataPoint 
{
	FXDataSource getDataSource();
	Instrument getInstrument();
	LocalDateTime getDateTime();
	Period getPeriod();
	long getBuyVolume();
	long getSellVolume();
}
