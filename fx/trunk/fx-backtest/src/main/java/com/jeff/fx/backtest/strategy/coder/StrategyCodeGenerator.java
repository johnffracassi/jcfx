package com.jeff.fx.backtest.strategy.coder;

import java.io.IOException;
import java.io.InputStream;

import com.jeff.fx.util.FileUtil;

public class StrategyCodeGenerator {

	private StrategyCodeEnhancer enhancer = new StrategyCodeEnhancer();
	
	public String buildClass(StrategyCodeModel model) {

		final String fieldsTemplate = getResource("fields.template");
		final String paramsTemplate = getResource("params.template");
		String fields = "";
		String params = "";

		if(model != null) {
			
			if(model.getParameters() != null && model.getParameters().size() > 0) {
				for(StrategyParam param : model.getParameters()) {
					String fieldsCode = fieldsTemplate;
					fieldsCode = fieldsCode.replaceAll("~name", param.getName());
					fieldsCode = fieldsCode.replaceAll("~type", param.getType().getName());
					fields += fieldsCode;
					
					String paramsCode = paramsTemplate;
					paramsCode = paramsCode.replaceAll("~name", param.getName());
					paramsCode = paramsCode.replaceAll("~type", param.getType().getName());
					params += paramsCode;
				}
			}
	
			String classTemplate = getResource("class.template");
			classTemplate = classTemplate.replaceAll("~className", model.getClassName());
			classTemplate = classTemplate.replaceAll("~openCode", enhancer.enhance(model.getOpenCode()));
			classTemplate = classTemplate.replaceAll("~closeCode", enhancer.enhance(model.getCloseCode()));
			classTemplate = classTemplate.replaceAll("~params", params);
			classTemplate = classTemplate.replaceAll("~fields", fields);
			
			return classTemplate;
		} else {
			return "";
		}
	}
	
	private String getResource(String name) {
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
