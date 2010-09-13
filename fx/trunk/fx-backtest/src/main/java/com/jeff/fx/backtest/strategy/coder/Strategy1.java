package com.jeff.fx.backtest.strategy.coder;

import static java.lang.Math.*;
import java.util.*;
import java.io.*;
import org.joda.time.*;
import static com.jeff.fx.common.CandleValueModel.*;
import com.jeff.fx.backtest.engine.*;
import com.jeff.fx.backtest.strategy.*;
import com.jeff.fx.backtest.strategy.coder.*;
import com.jeff.fx.common.*;
import com.jeff.fx.util.*;
import com.jeff.fx.indicator.*;
import com.sun.xml.internal.ws.developer.StreamingAttachment;


public class Strategy1 extends CodedStrategy {

	private List<StrategyParam> paramsList = new ArrayList<StrategyParam>();
	private IndicatorCache indicators;
	private CandleCollection candles;

	@Parameter("Open Time")
	@Description("Open a trade at this time")
	private com.jeff.fx.common.TimeOfWeek openTime;

	@Parameter("Close Time")
	@Description("Close the open trade at this time")
	private com.jeff.fx.common.TimeOfWeek closeTime;

	@Parameter("Short SMA")
	@Description("Only open the trade if SMA(n) is heading in direction of the offer side")
	private java.lang.Integer shortSma;


	public void setup(Map<String, Object> param, IndicatorCache indicators, CandleCollection candles) {
		
		this.indicators = indicators;
		this.candles = candles;

		openTime = (com.jeff.fx.common.TimeOfWeek)param.get("openTime");
		paramsList.add(new StrategyParam("openTime", com.jeff.fx.common.TimeOfWeek.class));
		closeTime = (com.jeff.fx.common.TimeOfWeek)param.get("closeTime");
		paramsList.add(new StrategyParam("closeTime", com.jeff.fx.common.TimeOfWeek.class));
		shortSma = (java.lang.Integer)param.get("shortSma");
		paramsList.add(new StrategyParam("shortSma", java.lang.Integer.class));
			
	}
	
	public String getName() {
		return "Strategy1";
	}

	public boolean open(CandleDataPoint candle, int idx) throws Exception {
		
		if(getOrderBook().getOpenOrders().size() == 0) {
		
			boolean open = false;
			BTOrder order = new BTOrder();
			order.setUnits(1.0);
			
			boolean smaAboveHigh = indicators.get("sma", candles, idx, 14,Typical) > candle.getBuyHigh();
			open = smaAboveHigh;
			
			if(open) {
				openOrder(order, candle);
				return true;
			}
		}
		
		return false;
	}

	public boolean close(CandleDataPoint candle, int idx) throws Exception {
		
		if(getOrderBook().getOpenOrders().size() > 0) {			
		
			BTOrder order = getOrderBook().getOpenOrders().get(0);
			boolean close = false;
			
			boolean smaAboveHigh = indicators.get("sma", candles, idx, 14,Typical) > candle.getBuyHigh();
				close = !smaAboveHigh;
			
			if(close) {
				closeOrder(order, candle);
				return true;
			}
		}
		
		return false;
	}
}