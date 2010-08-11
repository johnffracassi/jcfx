package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Period;

public class Scratch {

	public static void main(String[] args) {
		System.out.println(FXDataSource.Forexite.getCalendar().getOpenTime().periodOfWeek(Period.OneMin));
		System.out.println(FXDataSource.Forexite.getCalendar().getCloseTime().periodOfWeek(Period.OneMin));
	}
	
}
