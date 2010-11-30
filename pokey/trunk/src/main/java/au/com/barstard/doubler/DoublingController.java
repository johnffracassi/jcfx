package au.com.barstard.doubler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.barstard.GameEventListener;
import au.com.barstard.controlpanel.ControlPanelAdapter;

@Component
public class DoublingController extends ControlPanelAdapter
{
    @Autowired
    private List<GameEventListener> listeners;
    
    private DoublingView view;

    private int balance;
    private int cumulativeMultiplier;
    
    public DoublingController()
    {
        view = new DoublingView();
        
        balance = 0;
        cumulativeMultiplier = 1;
        
        view.getBtnBlack().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton(DoubleButton.Black);
            }
        });
        view.getBtnClubs().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton(DoubleButton.Club);
            }
        });
        view.getBtnSpades().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton(DoubleButton.Spade);
            }
        });
        view.getBtnRed().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton(DoubleButton.Red);
            }
        });
        view.getBtnHearts().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton(DoubleButton.Heart);
            }
        });
        view.getBtnDiamonds().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton(DoubleButton.Diamond);
            }
        });
        
    }

    public void startDouble(int balance)
    {
        if(getBalance() > 0 || getMultiplier() > 1)
        {
            throw new RuntimeException("Double already in progress, can't start another one");
        }
        
        setBalance(balance);
        cumulativeMultiplier = 1;
    }
    
    private void finishDouble(int amount, int multiplier, boolean win)
    {
        for(GameEventListener listener : listeners)
        {
            listener.gambleComplete(amount, multiplier, win);
        }
        
        setBalance(0);
        cumulativeMultiplier = 1;
    }
    
    public void takeWinPressed()
    {
        if(balance > 0)
        {
            finishDouble(balance, cumulativeMultiplier, true);
        }
    }
    
    private void hitButton(DoubleButton button)
    {
        // no balance to gamble? do nuffin...
        if(balance == 0)
        {
            return;
        }
        
        // pick a suit
        Suit actualSuit = Suit.random();
        
        // add to history panel
        view.getPnlHistory().addSuit(actualSuit);

        // how much we are gambling
        int creditsGambled = balance;
        
        // the gambling multiplier that we are up to
        int currentMultiplier = cumulativeMultiplier;
        
        // work out how much we won
        double multiplier = multiplier(button, actualSuit);
        cumulativeMultiplier = (int)(multiplier * cumulativeMultiplier);
        balance *= multiplier;
        setBalance(balance);

        // notify all listeners of what just happened
        boolean doubleWon = multiplier > 0.0;
        if(doubleWon)
        {
            notifyListenersOfGambleWin((int)multiplier);
        }
        else
        {
            nofityListenersOfGambleLoss((int)currentMultiplier);
            finishDouble(creditsGambled, currentMultiplier, false);
        }
    }

    private void notifyListenersOfGambleWin(int multiplier)
    {
        for(GameEventListener listener : listeners)
        {
            listener.gambleWon((int)multiplier);
        }
    }
    
    private void nofityListenersOfGambleLoss(int multiplier)
    {
        for(GameEventListener listener : listeners)
        {
            listener.gambleLost(multiplier);
        }
    }
    
    private double multiplier(DoubleButton btn, Suit suit)
    {
        switch(btn)
        {
            case Black: return (suit == Suit.Club || suit == Suit.Spade) ? 2 : 0;
            case Red: return (suit == Suit.Heart || suit == Suit.Diamond) ? 2 : 0;
            case Club: return (suit == Suit.Club) ? 4 : 0;
            case Diamond: return (suit == Suit.Diamond) ? 4 : 0;
            case Heart: return (suit == Suit.Heart) ? 4 : 0;
            case Spade: return (suit == Suit.Spade) ? 4 : 0;
            default: throw new RuntimeException("what the fuck?!");
        }
    }
    
    private void setBalance(int balance)
    {
        this.balance = balance;
        view.getLblRiskPayout().setText(String.format("$%1.2f (%dx)", balance / 100.0, cumulativeMultiplier));
    }
    
    public DoublingView getView()
    {
        return view;
    }

    private int getBalance()
    {
        return balance;
    }
    
    private int getMultiplier()
    {
        return cumulativeMultiplier;
    }
}

enum DoubleButton
{
    Red, Black, Club, Spade, Diamond, Heart
}