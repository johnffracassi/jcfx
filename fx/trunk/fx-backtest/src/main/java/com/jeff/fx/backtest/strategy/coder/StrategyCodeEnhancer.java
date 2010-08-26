package com.jeff.fx.backtest.strategy.coder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrategyCodeEnhancer {
	
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
		code = code.replaceAll("indicator\\.(\\w*)\\.", "indicators.getIndicator(\"$1\", idx).");		
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
