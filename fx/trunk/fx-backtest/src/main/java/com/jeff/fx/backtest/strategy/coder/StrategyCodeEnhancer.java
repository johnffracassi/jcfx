package com.jeff.fx.backtest.strategy.coder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StrategyCodeEnhancer {
	
	private static Logger log = Logger.getLogger(StrategyCodeEnhancer.class);
	
	public static void main(String[] args) {

		StrategyCodeEnhancer sce = new StrategyCodeEnhancer();
		String generated = sce.enhance(sce.example());
		System.out.println("=====");
		System.out.println(generated);
	}
	
	private String example() {
		
		return  "if(candle.date.dayOfWeek == 3) {\n" + 
				"  if(#sma(14,Typical).getDirection(3) > candle.buyHigh) {\n"+
				"    open = true;\n" + 
				"  }\n}\n";
	}	
	
	public String enhance(String code) {
		return getters(indicators(code));
	}

	private String indicators(String code) {
		
		log.debug("replacing indicator shortcuts with indicators.getValue");

		// eg - #sma(14,Typical)
		Pattern pattern = Pattern.compile("#(\\w+)\\((.*)\\)\\.");
		Matcher matcher = pattern.matcher(code);

		while(matcher.find()) {
			
			String found = matcher.group(0);
			String name = matcher.group(1);
			String args = matcher.group(2);
			
			System.out.println("found indicator = " + name + "(" + args + ")");
			
			String transformed = "indicators.get(\"" + name + "\", candles, idx, " + args + ").";
			code = code.replace(found, transformed);

			System.out.println("  replaced [" + found + "] with [" + transformed + "]");
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
			} else {
				System.out.println("  no substitution");
			}
		}
		
		return code;
	}

}
