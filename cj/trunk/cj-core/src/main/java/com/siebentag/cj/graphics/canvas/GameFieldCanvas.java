package com.siebentag.cj.graphics.canvas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.GameStateManager;
import com.siebentag.cj.game.BowlModel;
import com.siebentag.cj.game.action.BowlAction;
import com.siebentag.cj.game.action.BowlActionFactory;
import com.siebentag.cj.game.event.BallStartedEvent;
import com.siebentag.cj.game.shot.ShotRecorder;
import com.siebentag.cj.graphics.World;
import com.siebentag.cj.mvc.BatsmanController;
import com.siebentag.cj.mvc.Controller;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.time.TimeKeeper;
import com.siebentag.cj.util.math.Point3D;

@SuppressWarnings("serial")
@Component
public class GameFieldCanvas extends JComponent implements MouseMotionListener, MouseListener, Runnable
{
	private static final Logger log = Logger.getLogger(GameFieldCanvas.class);

	@Autowired
    private World world;
    
    @Autowired
    private Controller controller;
    
    @Autowired
    private BatsmanController batsmanController;
    
    @Autowired
    private TimeKeeper timeKeeper;
    
    @Autowired
    private GameStateManager gsm;
    
    @Autowired
    private ShotRecorder shotRecorder;
    
    @Autowired
    private BowlActionFactory baf;
   
    @Autowired
    private ManagedQueue managedQueue;
    
    @Autowired
    private List<CanvasElement> canvasElements;
    
    private boolean running = false;
    private static final double FPS_TARGET = 40.0; 
    private static final long SLEEP_TIME = (long)(1000.0 / FPS_TARGET);
    
	public GameFieldCanvas() 
    {
		log.debug("GameFieldCanvas constructor");
		
    	addMouseMotionListener(this);
    	addMouseListener(this);
    	setIgnoreRepaint(true);
    }

	public void init()
	{
		log.debug("Initialising GameFieldCanvas");
		Collections.sort(canvasElements);
	}

	/**
	 * Force a repaint, then sleep, then repaint, then sleep...
	 */
    public void run()
    {
		log.debug("Starting GameFieldCanvas render loop");

		while(!running)
    	{
    		repaint();
    		
    		try
    		{
    			Thread.sleep(SLEEP_TIME);
    		}
    		catch(Exception ex)
    		{
    		}
    	}
    }

    
    /**
     * Render loop for the game canvas
     */
    @Override public void paint(Graphics graphics)
    {
		double gameTime = timeKeeper.getTime();

		// setup the graphics object with anti-aliasing etc...
        Graphics2D g = (Graphics2D)graphics;    	
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// tell the world how big the viewport is
		world.setDimensions(getWidth(), getHeight());
		
		try
		{
			// paint all the canvas elements first
			for(CanvasElement element : canvasElements)
			{
				element.paint(g, gameTime);
			}
			
			// then paint all the players/umpires
			controller.paint(g, gameTime);
		}
		catch(Exception ex)
		{
        	ex.printStackTrace();
		}
    }
    
    
    public void mouseMoved(MouseEvent e)
    {
    	if(gsm.isRecordingShot())
    	{
    		shotRecorder.addPoint(e.getPoint());
    	}
    }
    
	public void mouseClicked(MouseEvent ev) 
	{
		log.debug("Mouse click caught on GameFieldCanvas (button=" + ev.getButton() + " / runningAllowed=" + gsm.isRunningAllowed() + " / ballInProgress=" + gsm.isBallInProgress() + ")");

		if(gsm.isRunningAllowed())
		{
			if(ev.getButton() == MouseEvent.BUTTON1)
			{
				batsmanController.queueRun(timeKeeper.getTime());
			}
			else if(ev.getButton() == MouseEvent.BUTTON3)
			{
				batsmanController.cancelQueuedRuns();
			}
		}
		else if(!gsm.isBallInProgress())
		{
			// alert listeners to the new ball, things should reset themselves here
			managedQueue.add(new BallStartedEvent());
			
	    	// TODO this needs to be executed in another thread
			BowlAction ba = baf.createEmptyBowlAction();
			ba.setTime(timeKeeper.getTime());
			
			// TODO bowl models needs to be generated somewhere meaningful
			BowlModel model = new BowlModel(new Point3D(1, 10, 2), 20, Math.PI * 0.98, 0.1);
			ba.setBowlModel(model);
			
			managedQueue.add(ba);
		}
	}
	
	public void mouseDragged(MouseEvent ev) {}
	public void mouseEntered(MouseEvent ev) {}
	public void mouseExited(MouseEvent ev) {}
	public void mousePressed(MouseEvent ev) {}
	public void mouseReleased(MouseEvent ev) {}

	public void setCanvasElements(List<CanvasElement> canvasElements)
    {
    	this.canvasElements = canvasElements;
    }
    
    public List<CanvasElement> getCanvasElements()
    {
    	return canvasElements;
    }
}
