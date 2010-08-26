package com.jeff.fx.backtest.engine;

import org.joda.time.LocalDate;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;

public abstract class AbstractIncrementalStrategy extends AbstractStrategy {
	
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
	public abstract void candle(CandleDataPoint candle); 
	
	/**
	 * Execute the test with all strategies
	 * 
	 * @param cc
	 * @return 
	 */
	public OrderBook execute(CandleCollection cc) {
		
		LocalDate date = cc.getStart();
		
		while(date.isBefore(cc.getEnd())) {
			CandleWeek cw = cc.getCandleWeek(date);
			for(int i=0, n=cw.getCandleCount(); i<n; i++) {
				candle(cw.getCandle(i));
			}
			date = date.plusDays(7);
		}
		
		return getOrderBook();
	}
}
