package com.jeff.fx.backtest.strategy.coder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;

public class Strategy1 extends CodedStrategy {

	private List<StrategyParam> paramsList = new ArrayList<StrategyParam>();
	private IndicatorCache indicators;
	private CandleCollection candles;
	
	private LocalDateTime openTime;
	private LocalDateTime closeTime;
	
	public String getName() {
		return "** Strategy1 **";
	}
	
	public List<StrategyParam> getParams() {
		return paramsList;
	}

	public void setup(Map<String, Object> param, IndicatorCache indicators, CandleCollection candles) {
		
		this.indicators = indicators;
		this.candles = candles;
		
		openTime = (LocalDateTime)param.get("openTime");
		paramsList.add(new StrategyParam("openTime", LocalDateTime.class));

		closeTime = (LocalDateTime)param.get("closeTime");
		paramsList.add(new StrategyParam("closeTime", LocalDateTime.class));
	}
	
	public boolean open(CandleDataPoint candle) {
		
		if(getOrderBook().getOpenOrders().size() == 0) {
			boolean open = false;
			BTOrder order = new BTOrder();
			
			/** body */
			
			if(open) {
				openOrder(order, candle);
				return true;
			}
		}
		
		return false;
	}

	public boolean close(CandleDataPoint candle) {
		
		if(getOrderBook().getOpenOrders().size() > 0) {			
			BTOrder order = getOrderBook().getOpenOrders().get(0);
			boolean close = false;
			
			/** body */
			
			if(close) {
				closeOrder(order, candle);
				return true;
			}
		}
		
		return false;
	}
}