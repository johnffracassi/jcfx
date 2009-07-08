package com.siebentag.cj.graphics.renderer;

import java.awt.Graphics2D;

import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.animation.AnimationFrame;
import com.siebentag.cj.mvc.FielderState;
import com.siebentag.cj.util.math.Point3D;

@Component
public class FielderRenderer extends AbstractRenderer<FielderState>
{
	public void render(Graphics2D g, Point3D loc, FielderState state, double animationTime)
	{
		AnimationFrame frame = getSpriteManager().getFrame("fielder", String.valueOf(state), animationTime);
		drawImage(g, loc, frame.getImage());
	}
}
