package com.siebentag.cj.graphics.animation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.graphics.sprite.SpriteLoader;

public class OneShotAnimation extends Animation
{
	private transient static final Logger log = Logger.getLogger("Animation");

	@Autowired
	private SpriteLoader spriteLoader;
	
	private List<AnimationFrame> frames;
	private double totalTime;

	public OneShotAnimation()
	{
		frames = new ArrayList<AnimationFrame>();
	}
	
	public int getFrameCount()
	{
		return frames.size();
	}
	
	public AnimationFrame getFrameByIndex(int frameIdx)
	{
		return frames.get(frameIdx);
	}

	public AnimationFrame getFrameByTime(double time)
	{
		return getFrameByIndex(getFrameIndexByTime(time));
	}
	
	public int getFrameIndexByTime(double time)
	{
		assert getFrameCount() > 0 : "No frames for animation " + getName();
		
		// requesting a frame past the end? Return the last frame
		if(time > totalTime)
		{
			return getFrameCount() - 1;
		}
		
		double accumulatedTime = 0.0;
		for(int frameNumber=0; frameNumber<getFrameCount(); frameNumber++)
		{
			accumulatedTime += getFrameByIndex(frameNumber).getDisplayTime();
			
			if(accumulatedTime > time)
			{
				return frameNumber;
			}
		}
		
		return 0;
	}
	
	public void addFrame(AnimationFrame frame)
	{
		frames.add(frame);
		setHeight(frame.getHeight());
		setWidth(frame.getWidth());
		totalTime += frame.getDisplayTime();
	}
	
	public void addFrame(String details)
	{
		String[] split = details.split(",");
		
		int tileX = Integer.parseInt(split[0]);
		int tileY = Integer.parseInt(split[1]);
		double delay = Double.parseDouble(split[2]);
		
		log.debug("adding tile [" + tileX + "," + tileY + "] for " + delay + "s");

		Image img = spriteLoader.getImage(tileX, tileY);
		
		AnimationFrame frame = new AnimationFrame(img, delay, "[" + tileX + "," + tileY + "]");
		
		addFrame(frame);
	}
	
	public void setFrames(List<String> details)
	{
		frames = new ArrayList<AnimationFrame>(details.size());
		
		for(String str : details)
		{
			log.debug("details: " + str);
			addFrame(str);
		}
	}
	
	public List<AnimationFrame> getFrames()
	{
		return frames;
	}

	public double getTotalTime()
	{
		return totalTime;
	}	
}
