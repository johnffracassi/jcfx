package com.jeff.fx.backtest.strategy;

import java.util.Arrays;
import java.util.Collection;
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

	private static Map<String,Class<? extends Indicator>> classLookup = new HashMap<String, Class<? extends Indicator>>();
	private Map<String,Indicator> descriptorLookup = new HashMap<String, Indicator>();
	private Map<Key,Indicator> calculatedLookup = new HashMap<Key, Indicator>();

	@Autowired
	public void setAllIndicators(List<Indicator> indicators) {
		for(Indicator indicator : indicators) {
			classLookup.put(indicator.getKey(), indicator.getClass());
		}
	}
	
	public static Collection<Class<? extends Indicator>> getAllIndicators() {
		return classLookup.values();
	}
	
	private Indicator createIndicatorInstance(String key) throws InstantiationException, IllegalAccessException {
		log.debug("creating new instance of " + key);
		Class<? extends Indicator> type = classLookup.get(key);
		log.debug("found class for " + key + ": " + type.getName());
		return type.newInstance();
	}
	
	public double get(String key, CandleCollection candles, int idx, Object ... params) throws Exception {

		log.debug("looking up indicator value: " + key + ",cc," + idx + "," + Arrays.toString(params));
		
		String instanceKey = key + "(" + Arrays.toString(params) + ")";
		log.debug("instanceKey = " + instanceKey + " (" + (descriptorLookup.containsKey(instanceKey)?"exists":"does not exits") + ")");
		
		if(descriptorLookup.containsKey(instanceKey)) {
			
			Indicator indicator = descriptorLookup.get(instanceKey);
			log.debug("found indicator in cache: " + indicator.getDisplayName());
			indicator = calculate(indicator, candles);
			log.debug(indicator.getDisplayName() + "[" + idx + "] = " + indicator.getValue(idx));
			return indicator.getValue(idx);
			
		} else {
			
			Indicator indicator = createIndicatorInstance(key);
			indicator.setParams(params);
			log.debug("setup indicator with params. " + indicator.getDisplayName());
			descriptorLookup.put(instanceKey, indicator);
			indicator = calculate(indicator, candles);
			log.debug(indicator.getDisplayName() + "[" + idx + "] = " + indicator.getValue(idx));
			return indicator.getValue(idx);
		}
	}
	
	public Indicator calculate(Indicator indicator, CandleCollection candles) {
		
		Key key = new Key(indicator, candles);

		if(calculatedLookup.containsKey(key)) {
			return calculatedLookup.get(key);
		} else {
			synchronized(this) {
				if(calculatedLookup.containsKey(key)) {
					return calculatedLookup.get(key);
				} else {				
					log.info("calculating " + indicator.getDisplayName());
					indicator.calculate(candles);
					calculatedLookup.put(new Key(indicator, candles), indicator);
					return indicator;
				}					
			}
		}
	}
	
	public void flush() {
		calculatedLookup.clear();
		descriptorLookup.clear();
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
