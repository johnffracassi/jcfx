package com.jeff.fx.backtest.strategy;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.indicator.Indicator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
	
	private Indicator createIndicatorInstance(String key)
    {
		log.debug("creating new instance of " + key);
		Class<? extends Indicator> type = classLookup.get(key);

        log.debug("found class for " + key + ": " + type.getName());
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public Indicator getIndicator(String key, Object ... params)
	{
        String instanceKey = key + "(" + Arrays.toString(params) + ")";
        log.debug("instanceKey = " + instanceKey + " (" + (descriptorLookup.containsKey(instanceKey)?"exists":"does not exist") + ")");

        if(descriptorLookup.containsKey(instanceKey))
        {
            Indicator indicator = descriptorLookup.get(instanceKey);
            log.debug("found indicator in cache: " + indicator.getDisplayName());
            return indicator;
        }
        else
        {
            Indicator indicator = createIndicatorInstance(key);
            indicator.setParams(params);
            log.debug("setup indicator with params. " + indicator.getDisplayName());
            descriptorLookup.put(instanceKey, indicator);
            return indicator;
        }
	}
	
	public double get(String key, CandleCollection candles, int idx, Object ... params)
    {
		log.debug("looking up indicator value: " + key + ",cc," + idx + "," + Arrays.toString(params));
		
		Indicator indicator = getIndicator(key, params);
		indicator = calculate(indicator, candles);
		return indicator.getValue(0, idx);
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
