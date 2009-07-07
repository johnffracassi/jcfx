package com.siebentag.cj.util.format;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.ExpressionEvaluator;

public class Formatter
{
	private static ExpressionEvaluator expressionEvaluator;
	private static Map<String,String> formats;
	private static SimpleDateFormat dateFormat;
	private static String dateFormatPattern;
	private static NumberFormat decimalFormat;
	private static String decimalFormatPattern;
	
	public Formatter() { }
	
	public static String format(Object model)
	{
		String className = model.getClass().getSimpleName();
		String format = formats.get(className);
		return format(format, model);
	}
	
	public static String format(String format, Object model)
	{
		try 
		{
			return (String)expressionEvaluator.evaluate(format, String.class, new ObjectVariableResolver(model), null);
		} 
		catch (ELException e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public static String format(String expression, Map<String,Object> model)
	{
		try 
		{
			return (String)expressionEvaluator.evaluate(expression, String.class, new HashMapVariableResolver(model), null);
		} 
		catch (ELException e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public void setFormats(Map<String,String> formats)
	{
		Formatter.formats = formats;
	}
	
	public Map<String,String> getFormats()
	{
		return formats;
	}
	
	public static String format(Date date)
	{
		return dateFormat.format(date);
	}
	
	public static String format(double num)
	{
		return decimalFormat.format(num);
	}
	
	public static String formatPoint(double x, double y)
	{
		return "[" + format(x) + "," + format(y) + "]";
	}
	
	public static String format(float num)
	{
		return decimalFormat.format(num);
	}
	
	public static String format(int num)
	{
		return String.valueOf(num);
	}
	
	public void setExpressionEvaluator(ExpressionEvaluator ee)
	{
		Formatter.expressionEvaluator = ee;
	}
	
	public void setDateFormatPattern(String dateFormatPattern)
	{
		dateFormat = new SimpleDateFormat(dateFormatPattern);
	}
	
	public void setDecimalFormatPattern(String decimalFormatPattern)
	{
		decimalFormat = new DecimalFormat(decimalFormatPattern);
	}
	
	public static SimpleDateFormat getDateFormat() 
	{
		return dateFormat;
	}

	public static String getDateFormatPattern() 
	{
		return dateFormatPattern;
	}

	public static NumberFormat getDecimalFormat() 
	{
		return decimalFormat;
	}

	public static String getDecimalFormatPattern() 
	{
		return decimalFormatPattern;
	}
}
