package com.siebentag.cj.graphics.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.renderer.BatsmanRenderer;
import com.siebentag.cj.graphics.sprite.SpriteManager;
import com.siebentag.cj.mvc.BatsmanState;
import com.siebentag.cj.time.TimeKeeper;

@Component
public class AnimationTester extends JFrame
{
	@Autowired
	TimeKeeper timeKeeper;
	
	@Autowired
	BatsmanRenderer batRend;
	
	@Autowired
	SpriteManager spriteManager;
	
	public AnimationTester()
	{
		build();
		setSize(600, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setIgnoreRepaint(true);

		new Thread() {
			public void run() {
				while(true)
				{
					try	{
						Thread.sleep(40);
						repaint();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private void build()
	{
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,100,100);

		double time = timeKeeper.getTime();
		String state = BatsmanState.StrikerWaiting.toString();
		AnimationFrame frame = spriteManager.getFrame("batsman", state, time);
		Image img = frame.getImage();
		g.drawImage(img, 10, 50, null);
		
		String state2 = BatsmanState.Idle.toString();
		AnimationFrame frame2 = spriteManager.getFrame("batsman", state2, time);
		Image img2 = frame2.getImage();
		g.drawImage(img2, 30, 50, null);
		
		g.setColor(Color.BLACK);
		g.drawString(String.format("%.2f", time), 10, 45);
	}
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			ApplicationContext ctx = new ClassPathXmlApplicationContext("/core.xml");
			
			SpriteManager sm = (SpriteManager)ctx.getBean("spriteManager");
			
			for(double time=0.0; time<10.0; time += 0.1)
			{
				AnimationFrame frame = sm.getFrame("batsman", BatsmanState.StrikerWaiting.toString(), time);
				System.out.format("time: %.1f / frame: %s\n", time, frame.getDescription());
			}
			
			AnimationTester at = (AnimationTester)ctx.getBean("animationTester");
		    at.setVisible(true);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
