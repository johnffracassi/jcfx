package com.siebentag.cj.graphics.sprite;

import java.util.HashMap;
import java.util.Map;

import com.siebentag.cj.graphics.animation.Animation;
import com.siebentag.cj.graphics.animation.AnimationFrame;

public class Sprite
{
	private String name;
	private Map<String,Animation> animations;
	
	public Sprite()
	{
		animations = new HashMap<String, Animation>();
	}
	
	public void addAnimation(String key, Animation anim)
	{
		animations.put(key, anim);
	}
	
	public void setAnimations(Map<String,Animation> animations)
	{
		this.animations = animations;
	}
	
	public Map<String,Animation> getAnimations()
	{
		return animations;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public Animation getAnimation(String key)
	{
		return animations.get(key);
	}
	
	public AnimationFrame getFrame(String key, double time)
	{
		return getAnimation(key).getFrameByTime(time);
	}
}

