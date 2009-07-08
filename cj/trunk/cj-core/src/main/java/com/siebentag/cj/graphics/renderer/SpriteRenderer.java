package com.siebentag.cj.graphics.renderer;

import java.awt.Graphics2D;

import com.siebentag.cj.graphics.animation.AnimationFrame;
import com.siebentag.cj.graphics.sprite.SpriteManager;
import com.siebentag.cj.util.math.Point3D;

public abstract class SpriteRenderer<StateClass> extends AbstractRenderer<StateClass>
{
	public abstract String getSpriteKey();

	public void render(Graphics2D g, Point3D loc, StateClass state, double animationTime)
	{
		SpriteManager spriteManager = getSpriteManager();
		AnimationFrame frame = spriteManager.getFrame(getSpriteKey(), String.valueOf(state), animationTime);
		drawImage(g, loc, frame.getImage());
	}
}
