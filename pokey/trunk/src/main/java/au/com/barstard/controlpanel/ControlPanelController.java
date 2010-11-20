package au.com.barstard.controlpanel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControlPanelController
{
    private ControlPanelView view;
    
    private int creditsPerLine = 1;
    private int lines = 1;
    
    @Autowired
    private List<ControlPanelListener> listeners = new ArrayList<ControlPanelListener>();
    
    public ControlPanelController()
    {
        view = new ControlPanelView(this);
    }
    
    public void addListener(ControlPanelListener listener)
    {
        listeners.add(listener);
    }
    
    public ControlPanelView getView()
    {
        return view;
    }
    
    public void setCreditsPerLine(int credits)
    {
        this.creditsPerLine = credits;
    }
    
    public void setLinesPlaying(int lines)
    {
        takeWin();
        
        this.lines = lines;
        
        for(ControlPanelListener listener : listeners)
        {
            listener.spin(lines, creditsPerLine);
        }
    }

    public void reserve()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.reserve();
        }
    }

    public void gamble()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.gamble();
        }
    }

    public void takeWin()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.takeWin();
        }
    }

    public void collect()
    {
        for(ControlPanelListener listener : listeners)
        {
            listener.collect();
        }
    }
}
