package au.com.barstard.doubler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.barstard.SoundPlayer;
import au.com.barstard.blokey.RecordKeeper;
import au.com.barstard.gamestate.GameStateModel;

@Component
public class DoublingController
{
    @Autowired
    private GameStateModel model;
    
    @Autowired
    private RecordKeeper recordKeeper;
    
    private DoublingView view;
    private int balance;
    private int cumulativeMultiplier = 1;
    
    public DoublingController()
    {
        view = new DoublingView();
        
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

    public void startDouble()
    {
        cumulativeMultiplier = 1;
    }
    
    public void finishDouble(int finalBalance)
    {
    }
    
    public void hitButton(DoubleButton button)
    {
        if(balance == 0)
        {
            return;
        }
        
        Suit actualSuit = Suit.random();
        view.getPnlHistory().addSuit(actualSuit);

        int creditsGambled = balance;
        int currentMultiplier = cumulativeMultiplier;
        
        double multiplier = multiplier(button, actualSuit);
        cumulativeMultiplier = (int)(multiplier * cumulativeMultiplier);
        balance *= multiplier;
        setBalance(balance);

        boolean doubleWon = multiplier > 0.0;
        
        if(model != null)
        {
            model.setGambleMultiplier((int)multiplier);
        }

        System.out.println(balance + " / " + multiplier + " / " + creditsGambled + " / " + model.getTotalBet());
        
        if(balance >= 5000)
        {
            System.out.println("plus pineapple!");
            SoundPlayer.play("pluspineapple");
        }
        else if(multiplier >= 4.0 && creditsGambled > 1.0 * (model.getTotalBet()))
        {
            System.out.println("take that!!");
            SoundPlayer.play("takethat");
        }
        
        if(recordKeeper != null)
        {
            if(doubleWon)
            {
                recordKeeper.doubleResult(true, cumulativeMultiplier, balance);
            }
            else
            {
                recordKeeper.doubleResult(false, currentMultiplier, creditsGambled);
            }
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
    
    public void setBalance(int balance)
    {
        this.balance = balance;
        view.getLblRiskPayout().setText(String.format("$%1.2f (%dx)", balance / 100.0, cumulativeMultiplier));
    }
    
    public DoublingView getView()
    {
        return view;
    }

    public int getBalance()
    {
        return balance;
    }
}

enum DoubleButton
{
    Red, Black, Club, Spade, Diamond, Heart
}