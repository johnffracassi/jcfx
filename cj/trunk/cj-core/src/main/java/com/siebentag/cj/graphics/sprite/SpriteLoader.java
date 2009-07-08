package com.siebentag.cj.graphics.sprite;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.siebentag.cj.Config;

@SuppressWarnings("serial")
@Component
public class SpriteLoader extends JPanel
{
	private Map<String, Image> cache;

	public SpriteLoader()
	{
		try
		{
			loadImage();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private BufferedImage loadImage() throws Exception
	{
		BufferedImage img = ImageIO.read(new File(Config.getDataDir() + "/images/sprites2.bmp"));
		cache = parseImages(img);
		return img;
	}

	public Image getImage(int x, int y)
	{
		return cache.get("[" + x + "," + y + "]");
	}

	private Map<String, Image> parseImages(BufferedImage img)
	{
		cache = new HashMap<String, Image>();

		int tileWidth = 19;
		int tileHeight = 19;

		for (int y = 0; y < 22; y++)
		{
			for (int x = 0; x < 31; x++)
			{
				CropImageFilter cif = new CropImageFilter(x * (1 + tileWidth) + 1, y * (1 + tileHeight) + 1, tileWidth, tileHeight - 2);
				AlphaFilter af = new AlphaFilter();

				FilteredImageSource fis = new FilteredImageSource(new FilteredImageSource(img.getSource(), af), cif);
				Image cropped = createImage(fis);
				cache.put("[" + y + "," + x + "]", cropped);
			}
		}

		return cache;
	}
}

class AlphaFilter extends RGBImageFilter
{
	public AlphaFilter()
	{
		canFilterIndexColorModel = true;
	}

	public int filterRGB(int x, int y, int rgb)
	{
		// Adjust the alpha value
		int alpha = (rgb >> 24) & 0xff;

		int r = (rgb & 0xff0000);
		int g = (rgb & 0x00ff00) >> 8;
		int b = (rgb & 0x0000ff) >> 16;

		if (r == 0 && b == 0 && (g == 128 || g == 144))
			alpha = 0;

		// Return the result
		return ((rgb & 0x00ffffff) | (alpha << 24));
	}
}
