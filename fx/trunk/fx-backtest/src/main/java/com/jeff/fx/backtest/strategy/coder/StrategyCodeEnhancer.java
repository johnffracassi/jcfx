package com.jeff.fx.backtest.strategy.coder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.jeff.fx.backtest.strategy.IndicatorCache;


public class StrategyCodeEnhancer {
	
	private static Logger log = Logger.getLogger(StrategyCodeEnhancer.class);

	public static void main(String[] args) {
		StrategyCodeEnhancer sce = new StrategyCodeEnhancer();
		System.out.println(sce.enhance(sce.example()));
	}
	
	public String enhance(String code) {
		return getters(indicators(code));
	}
	
	private String example() {
		return  "if(candle.date.dayOfWeek == 3) {\n" + 
				"  if(indicator.sma14.value > candle.buyHigh) {\n"+
				"    open = true;\n" + 
				"  }\n}\n";
	}
	
	private String indicators(String code) {
		log.debug("replacing indicator shortcuts with indicators.getValue");

		Pattern pattern = Pattern.compile("indicators\\.(\\w*)\\.");
		Matcher matcher = pattern.matcher(code);

		while(matcher.find()) {
			
			String field = matcher.group(1);
			System.out.println("matching = " + field);
			if(!field.startsWith(".get")) {
				String transformed = "indicators.getValue(\"" + field + "\", candles, idx).";
				System.out.println("  substituted " + transformed);
				code = code.replaceAll(field.substring(1), transformed);
			}
		}
		
		return code;
	}
	
	private String getters(String code) {
		
		Pattern pattern = Pattern.compile("\\.\\w+[^(\\.]??");
		Matcher matcher = pattern.matcher(code);

		while(matcher.find()) {
			
			String field = matcher.group();
			System.out.println("matching = " + field);
			if(!field.startsWith(".get")) {
				String transformed = "get" + Character.toUpperCase(field.charAt(1)) + field.substring(2) + "()";
				System.out.println("  substituted=" + transformed);
				code = code.replaceAll(field.substring(1), transformed);
			}
		}
		
		return code;
	}

}
