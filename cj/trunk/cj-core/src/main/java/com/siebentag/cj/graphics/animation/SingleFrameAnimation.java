package com.siebentag.cj.graphics.animation;

import java.awt.Image;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.graphics.sprite.SpriteLoader;

public class SingleFrameAnimation extends Animation
{
	private transient static final Logger log = Logger.getLogger("Animation");

	@Autowired
	private SpriteLoader spriteLoader;
	
	private AnimationFrame frame;

	public SingleFrameAnimation()
	{
	}
	
	public void setFrame(String details)
	{
		String[] split = details.split(",");
		
		int tileX = Integer.parseInt(split[0]);
		int tileY = Integer.parseInt(split[1]);
		
		log.debug("setting tile [" + tileX + "," + tileY + "] as single frame");

		Image img = spriteLoader.getImage(tileX, tileY);
		
		AnimationFrame frame = new AnimationFrame(img, 0.0, "Only frame");
		
		this.frame = frame;
	}
	
	public String getFrame()
	{
		return "";
	}
	
	@Override public AnimationFrame getFrameByTime(double time)
	{
		return frame;
	}
}
