package com.jeff.fx.backtest.strategy.coder;

import static java.lang.Math.*;
import java.io.*;
import org.joda.time.*;
import java.util.*;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.strategy.coder.*;
import com.jeff.fx.common.*;
import com.jeff.fx.util.*;
import com.jeff.fx.backtest.strategy.*;

public class Strategy1 extends CodedStrategy {

	public String getName() {
		return "** Strategy1 **";
	}

	public boolean open(CandleDataPoint candle, Map<String, Object> param, IndicatorCache indicators) {
		
		if(getOrderBook().getOpenOrders().size() == 0) {
			boolean open = false;
			BTOrder order = new BTOrder();
			
			// body
			
			if(open) {
				openOrder(order, candle);
				return true;
			}
		}
		
		return false;
	}

	public boolean close(CandleDataPoint candle, Map<String, Object> param, IndicatorCache indicators) {
		
		if(getOrderBook().getOpenOrders().size() > 0) {			
			BTOrder order = getOrderBook().getOpenOrders().get(0);
			boolean close = false;
			
			// body
			
			if(close) {
				closeOrder(order, candle);
				return true;
			}
		}
		
		return false;
	}
}