package au.com.barstard.controlpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.barstard.GameEventListener;

@Component
public class ControlPanelController
{
    @Autowired
    private List<GameEventListener> eventListeners;
    
    private ControlPanelView view;
    
    private int creditsPerLine = 1;
    private int lines = 1;
    
    @Autowired
    private List<ControlPanelListener> listeners = new ArrayList<ControlPanelListener>();
    
    public ControlPanelController()
    {
        view = new ControlPanelView(this);
    }
    
    public void show(java.awt.Component parent)
    {
        JFrame frame = new JFrame("Controller");
        frame.setSize(new Dimension(600, 200));
        frame.setLayout(new BorderLayout());
        frame.add(view, BorderLayout.CENTER);
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
    
    public void setCreditsPerLine(int credits)
    {
        this.creditsPerLine = credits;
        
        for(GameEventListener listener : eventListeners)
        {
            listener.betChanged(creditsPerLine, lines);
        }
    }
    
    public void setLinesPlaying(int lines)
    {
        takeWin();
        
        this.lines = lines;

        for(GameEventListener listener : eventListeners)
        {
            listener.betChanged(creditsPerLine, lines);
        }
        
        for(ControlPanelListener listener : listeners)
        {
            listener.spinPressed(lines, creditsPerLine);
        }
    }

    public void help()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.helpPressed();
        }
    }

    public void gamble()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.gamblePressed();
        }
    }

    public void takeWin()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.takeWinPressed();
        }
    }

    public void reset()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.resetPressed();
        }
    }
}
