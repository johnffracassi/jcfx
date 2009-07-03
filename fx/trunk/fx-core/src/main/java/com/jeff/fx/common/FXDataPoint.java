package com.jeff.fx.common;

import org.joda.time.LocalDateTime;

public interface FXDataPoint
{
	FXDataSource getDataSource();
	String getInstrument();
	LocalDateTime getDate();

	long getBuyVolume();
	long getSellVolume();
}
