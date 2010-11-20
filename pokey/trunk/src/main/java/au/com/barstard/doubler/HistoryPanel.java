package au.com.barstard.doubler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class HistoryPanel extends JPanel
{
    private static final int SIZE = 10;
    
    private List<Suit> history;
    private JLabel[] labels;
    
    public HistoryPanel()
    {
        setOpaque(false);
        
        prePopulate();
        
        String layoutStr = "";
        for(int i=0; i<SIZE; i++)
        {
            layoutStr += "[grow,fill]";
        }
        setLayout(new MigLayout("", layoutStr, "[grow,fill]"));

        labels = new JLabel[SIZE];
        for(int i=0; i<SIZE; i++)
        {
            labels[i] = new JLabel("X");
            labels[i].setOpaque(true);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            add(labels[i], "cell " + i + " 0");
        }
        
        update();
        
        labels[0].setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
    
    public void addSuit(Suit suit)
    {
        history.add(0, suit);
        update();
    }
    
    private void update()
    {
        for(int i=0; i<labels.length; i++)
        {
            labels[i].setText(history.get(i).getLabel());
            labels[i].setBackground(history.get(i).getDisplayColor());
        }
    }
    
    private void prePopulate()
    {
        history = new ArrayList<Suit>(100);
        
        for(int i=0; i<SIZE; i++)
        {
            history.add(Suit.random());
        }
    }
}
