package com.siebentag.fx.source.forexcom;

/**
 * Title:        Margin Trader Development
 * Description:
 *               WebServicesUtil provides utilities methods to connect
 *               to ASP.NET based web services via the HTTP GET Method
 * Copyright:    Copyright (c) 2001
 * Company:      Gain Capital
 * @author       Phil Cave
 * @version 1.0
 * Date          02 July 2002
 */

/* @PHILCAVE - Added File */

/**
 * Java Imports
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * WebServiceUtil Class Provides utility functions to call ASP.NET Web Services
 * using HTTP GET Request.
 */
public class WebServiceUtil
{
	static private int RESPONSE_BUFFER_INIT_SIZE = 512;
	static private int MAX_TRYS = 1;
	static private int DELAY_SLEEP_MS = 1000;

	public static void main(String[] args)
	{
		String authKey = getAuthenticationKey("jeffcann@gmail.com", "forex123", "GAPI");
		System.out.println("Authentication key: " + authKey);
		String rates = getRates(authKey);
		System.out.println("Rates\n" + rates);
		
		String[][] table = getRatesTable(rates);
		for(String[] row : table)
		{
			for(String col : row)
			{
				System.out.print(col + "\t");
			}
			
			System.out.print("\n");
		}
	}

	public static String[][] getRatesTable(String ratesStr)
	{
		String[] lines = ratesStr.split("\\$");
		String[][] table = new String[lines.length][];
		
		for(int i=0; i<lines.length; i++)
		{
			table[i] = lines[i].split("\\\\");
		}
		
		return table;
	}
	
	public static String getRates(String authkey)
	{
		Map<String,String> prop = new HashMap<String, String>();
		prop.put("Key", authkey);
		
		return extractString(callWebService("GetRatesBlotter", prop));
	}
	
	public static String getAuthenticationKey(String username, String password, String brand)
	{
		Map<String,String> prop = new HashMap<String, String>();
		prop.put("UserID", username);
		prop.put("PWD", password);
		prop.put("Brand", brand);
		
		return extractString(callWebService("GetRatesServerAuth", prop));
	}
	
	private static String extractString(String msg)
	{
		int idx = msg.indexOf(">", msg.indexOf(">")+1) + 1;
		return msg.substring(idx, msg.lastIndexOf("<"));
	}
	
	private static String propertiesToQueryString(Map<String,String> properties)
	{
		if(properties == null || properties.size() == 0)
		{
			return "";
		}
		
		String str = "";
		for(String key : properties.keySet())
		{
			str += ("&" + key + "=" + properties.get(key));
		}
		return "?" + str.substring(1);
	}
	
	public static String callWebService(String service)
	{
		return callWebService(service, null);
	}
	
	public static String callWebService(String service, Map<String,String> properties)
	{
		String webServiceURL = "http://api.efxnow.com/DEMOWebServices2.8/Service.asmx/" + service;
		boolean bPosting = true;
		
		// locals
		int tries = 0;

		String queryString = propertiesToQueryString(properties);
		
		if(bPosting) queryString = queryString.substring(1);
		
		do
		{
			tries++;

			try
			{
				// Construct a URL of the WebService + paramters
				URL webServiceAddress = new URL(webServiceURL);
				URLConnection serviceConnection = webServiceAddress.openConnection();

				// Set the HTTP connection parameters
				serviceConnection.setDoInput(true);
				serviceConnection.setDoOutput(bPosting); // using output if were posting
				serviceConnection.setUseCaches(false);
				serviceConnection.setDefaultUseCaches(false);

				// Set the appropriate parameters depending on the connect method
				if(bPosting && queryString != null)
				{
					serviceConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					serviceConnection.setRequestProperty("Content-Length", String.valueOf(queryString.length()));
				}
				else
				{
					serviceConnection.setRequestProperty("Content-Type", "xml/text");
				}

				// if we are posting the request then send the query string to webservice changes the method to a POST
				if (bPosting && queryString != null)
				{
					OutputStream out = serviceConnection.getOutputStream();
					out.write(queryString.getBytes());
					out.flush();
					out.close();
				}

				// Get the Response into a string for return
				// Get the input stream reader from the connection
				BufferedReader in = new BufferedReader(new InputStreamReader(serviceConnection.getInputStream()));

				// Create a string buffer for the response
				StringBuffer data = new StringBuffer(RESPONSE_BUFFER_INIT_SIZE);
				String line = in.readLine();
				while (line != null)
				{
					data.append(line);
					line = in.readLine();
				}
				in.close();

				// Get the data into a string format
				String response = data.toString();

				// Log the reponse and return the data
				return (response);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				try
				{
					Thread.sleep(DELAY_SLEEP_MS);
				}
				catch (Exception ex)
				{
				}
			}
		} while ((tries < MAX_TRYS));

		return null;
	}

}