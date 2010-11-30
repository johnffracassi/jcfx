package au.com.barstard.blokey;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import au.com.barstard.GameEventListener;
import au.com.barstard.cashbox.CashBoxController;
import au.com.barstard.controlpanel.ControlPanelController;
import au.com.barstard.controlpanel.ControlPanelListener;
import au.com.barstard.doubler.DoublingController;
import au.com.barstard.gamestate.GameStateController;
import au.com.barstard.gamestate.GameStateModel;
import au.com.barstard.payline.PayLineProcessor;
import au.com.barstard.reel.ReelController;
import au.com.barstard.spin.SpinListener;
import au.com.barstard.symbol.SymbolModel;

@Component
public class BlokeyPokey extends JFrame implements SpinListener, ControlPanelListener, GameEventListener
{
    @Autowired
    private List<GameEventListener> listeners;
    
    @Autowired
    private GameStateModel model;
    
    @Autowired
    private ReelController reelController;
    
    @Autowired
    private PayLineProcessor paylineProcessor;
    
    @Autowired
    private ControlPanelController controlPanel;
    
    @Autowired
    private CashBoxController gameSummary;
    
    @Autowired
    private DoublingController doubler;

    @Autowired
    private GameStateController gameStatus;
    
    private CardLayout cards = new CardLayout();
    private JPanel pnlCards = new JPanel(cards);
    
    private int winAmount = 0;
    private boolean doubling = false;
    private boolean spinning = false;
    
    public static void main(String[] args)
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("blokey.xml");
        BlokeyPokey pokey = ctx.getBean(BlokeyPokey.class);
        pokey.run();
    }

    public BlokeyPokey() {
    }

    public void run()
    {
        setVisible(true);
    }
    
    @PostConstruct
    public void init()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(46, 139, 87));

        pnlCards.setOpaque(false);
        pnlCards.add(reelController, "reels");
        pnlCards.add(doubler.getView(), "gamble");
        
//        pnlPaylines.setBackground(Color.black);
//        pnlPaylines.setPreferredSize(new Dimension(60, 500));
//        add(pnlPaylines, BorderLayout.WEST);
        
        JPanel pnlGame = new JPanel();
        pnlGame.setLayout(new BorderLayout());
        pnlGame.add(gameStatus.getView(), BorderLayout.NORTH);
        pnlGame.add(pnlCards, BorderLayout.CENTER);
        pnlGame.add(gameSummary.getView(), BorderLayout.SOUTH);
        
        getContentPane().add(pnlGame, BorderLayout.NORTH);
        getContentPane().add(controlPanel.getView(), BorderLayout.SOUTH);
        
        setSize(600, 1000);
        
        resetPressed();
    }

    public void spinComplete(SymbolModel[][] activeSymbols)
    {
        int[] wins = paylineProcessor.calculateWin(activeSymbols);

        for (int i = 0; i < wins.length; i++)
        {
            winAmount += wins[i];
        }

        winAmount *= model.getCreditsPerLine();
        for(GameEventListener listener : listeners)
        {
            listener.spinComplete(winAmount);
        }
        
        spinning = false;
    }
    
    private void hideDoublingPanel()
    {
        cards.show(pnlCards, "reels");
    }
    
    @Override
    public void spinPressed(int lines, int credits)
    {
        if(!spinning)
        {
            spinning = true;
            
            takeWinPressed();
            
            model.setLinesPlayed(lines);
            model.setCreditsPerLine(credits);
            
            if(model.getTotalBet() <= model.getBalance())
            {
                model.decreaseBalance(model.getTotalBet());
                hideDoublingPanel();
                reelController.startSpinning();
                
                for(GameEventListener listener : listeners)
                {
                    listener.spinStarted(credits, lines);
                }
            }
        }
    }

    @Override
    public void helpPressed()
    {
    }

    @Override
    public void gamblePressed()
    {
        if(winAmount > 0)
        {
            doubling = true;
            cards.show(pnlCards, "gamble");
            doubler.startDouble(winAmount);
        }
    }

    @Override
    public void takeWinPressed()
    {
        hideDoublingPanel();

        // call on this object first, so model is updated
        takeWin(winAmount);
    }

    @Override
    public void resetPressed()
    {
        model.increaseBalance(1000);
        gameSummary.cashIn(1000);
    }

    @Override
    public void machineStarted()
    {
    }

    @Override
    public void cashIn(int amount)
    {
    }

    @Override
    public void cashOut(int amount)
    {
    }

    @Override
    public void spinStarted(int credits, int lines)
    {
    }

    @Override
    public void spinComplete(int winAmount)
    {
        // do nothing here
    }

    @Override
    public void takeWin(int amount)
    {
        if(!doubling)
        {
            model.increaseBalance(amount);
            winAmount = 0;
            doubling = false;

            for(GameEventListener listener : listeners)
            {
                if(listener != this)
                {
                    listener.takeWin(winAmount);
                }
            }
        }
    }

    @Override
    public void gambleWon(int multiplier)
    {
    }

    @Override
    public void gambleLost(int multiplier)
    {
    }

    @Override
    public void gambleComplete(int amount, int multiplier, boolean win)
    {
        doubling = false;
        takeWin(win ? amount : 0);
    }

    @Override
    public void betChanged(int creditsPerLine, int lines)
    {
    }
}