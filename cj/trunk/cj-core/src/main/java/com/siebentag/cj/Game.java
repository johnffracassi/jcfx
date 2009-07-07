package com.siebentag.cj;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Game
{
	private static final Logger log = Logger.getLogger(Game.class);

	@Autowired
	private GameFrame frame; 
	
	@Autowired
	private Manager manager;
	
	public static void main(String[] args)
	{
		log.info("Setting up Spring context (root beans def is " + Config.getDataDir() + "/core.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/core.xml");
		log.info("Spring context setup complete");
		
		Game game = (Game)ctx.getBean("game");
		game.run();
	}

	private void run()
	{
		log.info("Starting game");
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		frame.init();
		frame.setVisible(true);
		frame.startRenderLoop();
		
		// start the action/event queue
		manager.start();
	}
}