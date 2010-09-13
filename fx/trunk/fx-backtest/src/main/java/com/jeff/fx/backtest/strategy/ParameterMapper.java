package com.jeff.fx.backtest.strategy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.strategy.coder.Description;
import com.jeff.fx.backtest.strategy.coder.Parameter;
import com.jeff.fx.backtest.strategy.coder.StrategyParam;
import com.jeff.fx.backtest.strategy.coder.StrategyParamValue;
import com.jeff.fx.backtest.strategy.time.TimeStrategy;
import com.jeff.fx.common.TimeOfWeek;

public class ParameterMapper {

	public static void main(String[] args) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("takeProfit", 250);
		params.put("stopLoss", 250);
		params.put("shortSma", 14);
		params.put("longSma", 197);
		params.put("open", new TimeOfWeek(1, 10, 00));
		params.put("close", new TimeOfWeek(2, 22, 00));
		
		TimeStrategy ts = new TimeStrategy(1, params, new IndicatorCache());
		
		System.out.println(mapValues(ts));
	}
	
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
