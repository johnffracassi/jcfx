package com.siebentag.cj.util.format;

import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.VariableResolver;

public class HashMapVariableResolver implements VariableResolver
{
	private Map<String,Object> model;
	
	public HashMapVariableResolver(Map<String,Object> model)
	{
		this.model = model;
	}
	
	public Object resolveVariable(String key) throws ELException 
	{
		return model.get(key);
	}
}