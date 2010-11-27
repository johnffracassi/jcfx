package au.com.barstard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReelController extends JPanel implements SpinPanelListener
{
    @Autowired
    private List<SpinListener> listeners;
    
    private List<ReelModel> reels;
    
    private SpinView[] pnlSpin = new SpinView[5];
    private boolean[] isSpinning = new boolean[5];

    @PostConstruct
    private void init()
    {
        setPreferredSize(new Dimension(pnlSpin.length * SpinView.symbolTotalWidth, 3 * SpinView.symbolTotalWidth));
        setOpaque(false);

        for(int i=0; i<pnlSpin.length; i++)
        {
            pnlSpin[i] = new SpinView(this, 13 + i*7, (int)(Math.random() * reels.get(i).size()), reels.get(i).getSymbols());
        }

        setLayout(null);
        
        for(int i=0; i<5; i++)
        {
            pnlSpin[i].setBackground(Color.WHITE);
            pnlSpin[i].setBounds(SpinView.symbolGap + i * SpinView.symbolTotalWidth, SpinView.symbolGap, SpinView.symbolTotalWidth, 3 * SpinView.symbolTotalWidth);
            add(pnlSpin[i]);
        }
    }

    public void startSpinning()
    {
        for (int i = 0; i < 5; i++)
        {
            isSpinning[i] = true;
            pnlSpin[i].startSpinning();
        }
    }

    private int getIndexOf(Object reel)
    {
        for (int i = 0; i < 5; i++)
        {
            if (reel == pnlSpin[i])
            {
                return i;
            }
        }
        return -1;
    }

    public void spinComplete(SpinView reel)
    {
        isSpinning[getIndexOf(reel)] = false;

        boolean stillGoing = false;

        for(int i=0; i<3; i++)
        {
            
            System.out.println(i + ": " + reel.getActiveSymbols()[i].getName());
            
            if(reel.getActiveSymbols()[i].getName().equalsIgnoreCase("Boonie"))
            {
                SoundPlayer.play("boonie");
            }
        }
        
        for (int i = 0; i < 5; i++)
        {
            if (isSpinning[i])
            {
                stillGoing = true;
            }
        }

        if (stillGoing == false)
        {
            spinComplete();
        }
    }

    public void spinComplete()
    {
        for(SpinListener listener : listeners)
        {
            listener.spinComplete(getActiveSymbols());
        }
    }

    public SymbolModel[][] getActiveSymbols()
    {
        SymbolModel[][] block = new SymbolModel[5][];

        for (int i = 0; i < 5; i++)
        {
            block[i] = pnlSpin[i].getActiveSymbols();
        }

        return block;
    }

    public List<ReelModel> getReels()
    {
        return reels;
    }

    public void setReels(List<ReelModel> reels)
    {
        this.reels = reels;
    }
}
