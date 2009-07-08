package com.siebentag.cj.graphics.renderer;

import java.awt.Graphics2D;

import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.animation.AnimationFrame;
import com.siebentag.cj.mvc.BowlerState;
import com.siebentag.cj.util.math.Point3D;

@Component
public class BowlerRenderer extends AbstractRenderer<BowlerState>
{
	public void render(Graphics2D g, Point3D loc, BowlerState state, double animationTime)
	{
		AnimationFrame frame = getSpriteManager().getFrame("bowler", state.toString(), animationTime);
		drawImage(g, loc, frame.getImage());
	}
}
