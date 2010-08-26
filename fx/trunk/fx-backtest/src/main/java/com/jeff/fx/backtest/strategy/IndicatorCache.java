package com.jeff.fx.backtest.strategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.indicator.Indicator;

public class IndicatorCache {
	
	private static Logger log = Logger.getLogger(IndicatorCache.class);

	private Map<Key,Indicator> map = new HashMap<Key, Indicator>();
	private Object lock = new Object();
	
	public Indicator calculate(Indicator indicator, CandleCollection candles) {
		
		Key key = new Key(indicator, candles);

		if(map.containsKey(key)) {
			return map.get(key);
		} else {
			synchronized(lock) {
				if(map.containsKey(key)) {
					return map.get(key);
				} else {				
					log.info("calculating " + indicator.getName());
					indicator.calculate(candles);
					map.put(new Key(indicator, candles), indicator);
					return indicator;
				}					
			}
		}
	}
	
	public void flush() {
		map.clear();
	}
}

class Key {
	
	public Indicator indicator;
	public CandleCollection candles;
	
	public Key(Indicator i, CandleCollection cc) {
		indicator = i;
		candles = cc;
	}
	
	public int hashCode() {
		return indicator.hashCode() + candles.hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Key) {
			Key key = (Key)obj;
			return (key.indicator.getName().equals(indicator.getName()) && key.candles == candles);
		}
		return false;
	}
}
