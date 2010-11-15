package com.siebentag.cj.util.format;

import java.awt.Color;

public class HexColourParser 
{
	public Color hexColor(String hexString)
	{
		Color result = null;

		int hexLength = hexString.length();
		try
		{
			int red = Integer.parseInt(hexString.substring(1, 3), 16);
			int green = Integer.parseInt(hexString.substring(3, 5), 16);
			int blue = Integer.parseInt(hexString.substring(5, 7), 16);
			if (hexLength == 7)
			{
				result = new Color(red, green, blue);
			}
			else
			{
				int alpha = Integer.parseInt(hexString.substring(7, 9), 16);
				result = new Color(red, green, blue, alpha);
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		return (result);
	}
}
