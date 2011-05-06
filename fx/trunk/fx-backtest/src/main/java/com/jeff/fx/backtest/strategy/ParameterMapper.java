package com.jeff.fx.backtest.strategy;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.strategy.coder.Description;
import com.jeff.fx.backtest.strategy.coder.Parameter;
import com.jeff.fx.backtest.strategy.coder.StrategyParam;
import com.jeff.fx.backtest.strategy.coder.StrategyParamValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParameterMapper {

	private static StrategyParam getStrategyParam(Field field) {
		
		if(field.getAnnotation(Parameter.class) != null) {
			
			String name = field.getName();
			String label = field.getAnnotation(Parameter.class).value();
			String description = field.getAnnotation(Description.class).value();
			Class<?> type = field.getType();

			StrategyParam param = new StrategyParam(name, type);
			param.setDescription(description);
			param.setLabel(label);

			return param;
			
		} else {
			return null;
		}
	}
	
	public static Map<String,StrategyParamValue> mapValues(AbstractStrategy strategy) {
		
		Map<String,StrategyParamValue> map = new HashMap<String,StrategyParamValue>();

		Field[] fields = strategy.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			StrategyParam param = getStrategyParam(field);
			if(param != null) {
				try {
					Object returnValue = strategy.getClass().getMethod(param.getGetter()).invoke(strategy);
					StrategyParamValue value = new StrategyParamValue(param, returnValue);
					map.put(param.getName(), value);
				} catch(Exception e) {
					// in theory, this should never happen!
					e.printStackTrace();
				}
			}
		}
		
		return map;		
	}
	
	public static Map<String,StrategyParam> mapParameters(AbstractStrategy strategy) {
		
		Map<String,StrategyParam> map = new HashMap<String,StrategyParam>();

		Field[] fields = strategy.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			StrategyParam param = getStrategyParam(field);
			if(param != null) {
				map.put(param.getName(), param);
			}
		}
		
		return map;
	}
}
