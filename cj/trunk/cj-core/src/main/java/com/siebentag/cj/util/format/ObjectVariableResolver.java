package com.siebentag.cj.util.format;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.VariableResolver;

public class ObjectVariableResolver implements VariableResolver
{
	private Object model;
	
	public ObjectVariableResolver(Object model)
	{
		this.model = model;
	}
	
	public Object resolveVariable(String key) throws ELException 
	{
        try
        {
        	String methodName = "get" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
        	Method getter = model.getClass().getMethod(methodName);
			return getter.invoke(model);
        }
        catch (SecurityException e)
        {
	        throw new ELException(e);
        }
        catch (NoSuchMethodException e)
        {
	        throw new ELException(e);
        }
        catch (IllegalArgumentException e)
        {
	        throw new ELException(e);
        }
        catch (IllegalAccessException e)
        {
	        throw new ELException(e);
        }
        catch (InvocationTargetException e)
        {
	        throw new ELException(e);
        }
	}
}