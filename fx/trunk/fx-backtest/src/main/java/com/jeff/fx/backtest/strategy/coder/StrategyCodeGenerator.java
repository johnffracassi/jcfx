package com.jeff.fx.backtest.strategy.coder;

import java.io.IOException;
import java.io.InputStream;

import com.jeff.fx.util.FileUtil;

public class StrategyCodeGenerator {

	protected String buildClass(String className, String openCode, String closeCode) {
		
		String template = getResource("class.template");

		template = template.replaceAll("~className", className);
		template = template.replaceAll("~openCode", openCode);
		template = template.replaceAll("~closeCode", closeCode);
		
		return template;
	}
	
	protected String getResource(String name) {
		try {
			String loc = ("/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/") + name;
			InputStream is = StrategyCodeGenerator.class.getResourceAsStream(loc);
			return FileUtil.asString(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ERROR READING RESOURCE:" + name;
	}
}
