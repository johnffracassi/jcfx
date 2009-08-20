package com.siebentag.cj.graphics.sprite;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.World;
import com.siebentag.cj.graphics.animation.Animation;
import com.siebentag.cj.graphics.animation.AnimationFrame;
import com.siebentag.cj.util.math.Point3D;

@Component
public class SpriteManager
{
	private transient static final Logger log = Logger.getLogger("Animation");

	private Map<String,Sprite> spriteMap;
	
	@Autowired
	private World world;

	public SpriteManager()
	{
	}

	public void setSprites(Map<String,Sprite> spriteMap)
	{
		log.debug("setting sprite map");
		this.spriteMap = spriteMap;
	}

	public Sprite getSprite(String key)
	{
		if(!spriteMap.containsKey(key)) 
		{
			log.error("sprite " + key + " is not defined");
		}
			
		return spriteMap.get(key);
	}
	
	public Animation getAnimation(String spriteKey, String animationKey)
	{
		if(getSprite(spriteKey).getAnimation(animationKey) == null)
		{
			log.error("sprite " + spriteKey + " does not contain animation " + animationKey);
		}

		return getSprite(spriteKey).getAnimation(animationKey);
	}
	
	public AnimationFrame getFrame(String spriteKey, String animationKey, double animationTime)
	{
		return getAnimation(spriteKey, animationKey).getFrameByTime(animationTime);
	}
	
	public void draw(Graphics2D g, Point3D loc, Image img)
	{
		Point2D loc2d = world.convert(loc);
		g.drawImage(img, (int) loc2d.getX() - (img.getWidth(null) / 2), (int) loc2d.getY() - img.getHeight(null), null);
	}
}
