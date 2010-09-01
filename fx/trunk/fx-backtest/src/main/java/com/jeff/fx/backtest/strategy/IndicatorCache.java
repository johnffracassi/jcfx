package com.jeff.fx.backtest.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.indicator.Indicator;

@Component
public class IndicatorCache {
	
	private static Logger log = Logger.getLogger(IndicatorCache.class);

	private static List<Indicator> allIndicators;
	
	private Map<Key,Indicator> map = new HashMap<Key, Indicator>();
	private Map<String,Indicator> lookupByName = new HashMap<String, Indicator>();

	@Autowired
	public void setAllIndicators(List<Indicator> indicators) {
		System.out.println("found " + indicators.size() + " indicators");
		allIndicators = indicators;
	}
	
	public static List<Indicator> getAllIndicators() {
		return allIndicators;
	}
	
	public Object evaluate(String key, CandleCollection candles, int idx) {
		Indicator indicator = lookupByName.get(key);
		indicator = map.get(new Key(indicator, candles));
		return indicator.getValue(idx);
	}
	
	public Indicator calculate(Indicator indicator, CandleCollection candles) {
		
		Key key = new Key(indicator, candles);

		if(map.containsKey(key)) {
			return map.get(key);
		} else {
			synchronized(this) {
				if(map.containsKey(key)) {
					return map.get(key);
				} else {				
					log.info("calculating " + indicator.getDisplayName());
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
			return (key.indicator.getDisplayName().equals(indicator.getDisplayName()) && key.candles == candles);
		}
		return false;
	}
}
