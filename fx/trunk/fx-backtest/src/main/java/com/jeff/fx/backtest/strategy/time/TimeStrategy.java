package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.BTParameterSet;
import com.jeff.fx.backtest.engine.BTParameterTable;
import com.jeff.fx.common.CandleDataPoint;

public class TimeStrategy extends AbstractStrategy {
	
	private int openAtDay = 0;
	private LocalTime openAtTime = null;
	private int closeAtDay = 0;
	private LocalTime closeAtTime = null;
	
	public TimeStrategy(int id, int openAtDay, LocalTime openAtTime, int closeAtDay, LocalTime closeAtTime) {
		
		super(id);
		
		this.openAtDay = openAtDay;
		this.openAtTime = openAtTime;
		this.closeAtDay = closeAtDay;
		this.closeAtTime = closeAtTime;
	}
	
	public static List<TimeStrategy> createTestSet(BTParameterSet ps) {
		
		double[][] parameters = BTParameterTable.getParameterValueTable(ps);
		int permutations = parameters[0].length;
		
		List<TimeStrategy> list = new ArrayList<TimeStrategy>(permutations);
		for(int i=0; i<permutations; i++) {
//			TimeStrategy strategy = new TimeStrategy(i+1, new double[] { parameters[0][i], parameters[1][i] });
//			list.add(strategy);
		}
		
		return list;
	}
	
	public void candle(CandleDataPoint candle) {
		
		int dayOfWeek = candle.getDate().getDayOfWeek();
		LocalTime time = candle.getDate().toLocalTime();
		
		// is it open/close time?
		if(openAtDay == dayOfWeek && time.getHourOfDay() == openAtTime.getHourOfDay() && time.getMinuteOfHour() == openAtTime.getMinuteOfHour() && !hasOpenOrder()) {
			open(new BTOrder(), candle);
		} else if(closeAtDay == dayOfWeek && time.getHourOfDay() == closeAtTime.getHourOfDay() && time.getMinuteOfHour() == closeAtTime.getMinuteOfHour() && hasOpenOrder()) {
			close(candle);
		} 
	}


	@Override
	public String toString() {
		return getId() + ". " + openAtDay + " to " + closeAtDay + " = " + getBalance() + " (" + getWins() + "/" + getLosses() + ")";
	}
}
