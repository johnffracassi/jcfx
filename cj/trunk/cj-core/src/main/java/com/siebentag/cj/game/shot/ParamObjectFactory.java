package com.siebentag.cj.game.shot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.siebentag.cj.game.math.prob.ProbabilityModel;

public class ParamObjectFactory 
{
	private static final Logger log = Logger.getLogger("Shots");

	public static Consequence createConsequence(ParamObject param)
	{
		return (Consequence)create(param);
	}
	
	public static ProbabilityModel createProbability(ParamObject param)
	{
		return (ProbabilityModel)create(param);
	}
	
	@SuppressWarnings("unchecked")
	private static Object create(ParamObject param) 
	{
		try 
		{
			log.debug("create Consequence of class " + param.getType());
			Class clazz = Class.forName(param.getType());
			
			log.debug("instantiating " + param.getType());
			Object obj = clazz.newInstance();

			for (String key : param.getParams().keySet()) 
			{
				String val = param.getParams().get(key);
				log.debug("==> set " + key + " to " + val);
				
				String setterName = "set" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
				Method setterMethod = clazz.getMethod(setterName, String.class);
				setterMethod.invoke(obj, val);
			}

			return obj;
		} 
		catch (ClassNotFoundException cnfex) 
		{
			cnfex.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		}

		return null;
	}
}
