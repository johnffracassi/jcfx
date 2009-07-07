package com.siebentag.cj;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.canvas.GameFieldCanvas;
import com.siebentag.gui.JMenuBarSpring;

@Component
public class GameFrame extends JXFrame
{
	private static final long serialVersionUID = 1360721808728057780L;
	private static final Logger log = Logger.getLogger(GameFrame.class);
	
	@Autowired
	private GameFieldCanvas gameFieldCanvas;
	
	public GameFrame() 
    {
    }

	public void init()
	{
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

		JXPanel sth = frame("South", new JXPanel());
		JXPanel pnlEast = frame("East", new JXPanel());
		JXPanel pnlSouthEast= frame("South East", new JXPanel());

        JXPanel pnlSouth = new JXPanel(new BorderLayout());
        pnlSouth.add(sth, BorderLayout.CENTER);
        pnlSouth.add(pnlSouthEast, BorderLayout.EAST);

        add(gameFieldCanvas, BorderLayout.CENTER);
        add(pnlEast, BorderLayout.EAST);
        add(pnlSouth, BorderLayout.SOUTH);
        
//		final JSlider sldAngle = new JSlider(100, 8999, 3000);
//		sldAngle.addChangeListener(new ChangeListener() {
//			@Override public void stateChanged(ChangeEvent e) {
//				((World)SpringCricket.getBean("world")).setRotation(sldAngle.getValue() / 100.0, 0.0);
//			}
//		});
//		getToolBar().add(sldAngle);
        
        gameFieldCanvas.init();
        
	}

	@Autowired
	public void setMenu(JMenuBarSpring menubar)
	{
		setJMenuBar(menubar);
	}
	
	public void setGameFieldCanvas(GameFieldCanvas canvas)
	{
		this.gameFieldCanvas = canvas;
	}
	
	public GameFieldCanvas getGameFieldCanvas()
	{
		return gameFieldCanvas;
	}

	public void startRenderLoop()
	{
		new Thread(gameFieldCanvas, "RepaintThread").start();
	}
	
	private JXPanel frame(String title, JPanel pnl)
	{
        JXTitledPanel titled = new JXTitledPanel(title, pnl);
        Border shadow = new DropShadowBorder(Color.BLACK, 3, 0.66f, 3, false, false, true, true);
        Border line = BorderFactory.createLineBorder(Color.GRAY);
        Border border = BorderFactory.createCompoundBorder(shadow, line);
        titled.setBorder(border);
        return titled;
	}
	
}
