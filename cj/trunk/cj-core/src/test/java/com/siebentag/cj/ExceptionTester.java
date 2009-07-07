package com.siebentag.cj;

public class ExceptionTester 
{
	public static void main(String[] args) 
	{
		try
		{
			throwCjException();
			throwBIE();
		}
		catch(BadInputException bex)
		{
			System.out.println(bex);
		}
		catch(CJException cex)
		{
			System.out.println(cex);
		}
	}
	
	public static void throwCjException() throws CJException
	{
		throw new CJException();
	}
	
	public static void throwBIE() throws BadInputException
	{
		throw new BadInputException();
	}
}

class CJException extends Exception
{
}

class BadInputException extends CJException
{
}