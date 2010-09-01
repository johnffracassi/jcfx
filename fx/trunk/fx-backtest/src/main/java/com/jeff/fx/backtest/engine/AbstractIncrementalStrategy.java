package com.jeff.fx.backtest.engine;

import org.joda.time.LocalDate;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;

public abstract class AbstractIncrementalStrategy extends AbstractStrategy {
	
	private boolean keepGoing = true;
	
	/**
	 * @param id
	 */
	public AbstractIncrementalStrategy(int id) {
		super(id);
	}

	/**
	 * alert the strategy of the next candle
	 * @param candle
	 */
	public abstract void candle(CandleDataPoint candle, int idx); 
	
	/**
	 * Execute the test with all strategies
	 * 
	 * @param cc
	 * @return 
	 */
	public OrderBook execute(CandleCollection cc) {
		
		LocalDate date = cc.getStart();
		
		int idx = 0;
		while(keepGoing && date.isBefore(cc.getEnd())) {
			CandleWeek cw = cc.getCandleWeek(date);
			for(int i=0, n=cw.getCandleCount(); i<n && keepGoing; i++) {
				candle(cw.getCandle(i), idx++);
			}
			date = date.plusDays(7);
			
			if(!keepGoing) 
				break;
		}
		
		return getOrderBook();
	}
	
	public void stop() {
		keepGoing = false;
	}
}
