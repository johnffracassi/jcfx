package au.com.barstard.blokey;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import au.com.barstard.PayLineProcessor;
import au.com.barstard.ReelController;
import au.com.barstard.SpinListener;
import au.com.barstard.SymbolModel;
import au.com.barstard.controlpanel.ControlPanelController;
import au.com.barstard.controlpanel.ControlPanelListener;
import au.com.barstard.doubler.DoublingController;
import au.com.barstard.gamestate.GameStateController;
import au.com.barstard.gamestate.GameStateModel;
import au.com.barstard.gamestate.GameStateModelListener;

@Component
public class BlokeyPokey extends JFrame implements SpinListener, ControlPanelListener
{
    @Autowired
    private GameStateModel model;
    
    @Autowired
    private ReelController reelController;
    
    @Autowired
    private PayLineProcessor paylineProcessor;
    
    @Autowired
    private ControlPanelController controlPanel;
    
    @Autowired
    private DoublingController doubler;

    @Autowired
    private GameStateController gameStatus;
    
//    private PayLinePanel pnlPaylines = new PayLinePanel();

    private CardLayout cards = new CardLayout();
    JPanel pnlCards = new JPanel(cards);
    
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

        pnlCards.setOpaque(false);
        pnlCards.add(reelController, "reels");
        pnlCards.add(doubler.getView(), "gamble");
        
//        pnlPaylines.setBackground(Color.black);
//        pnlPaylines.setPreferredSize(new Dimension(60, 500));
//        add(pnlPaylines, BorderLayout.WEST);
        
        getContentPane().setBackground(new Color(46, 139, 87));
        getContentPane().add(gameStatus.getView(), BorderLayout.NORTH);
        getContentPane().add(pnlCards, BorderLayout.CENTER);
        getContentPane().add(controlPanel.getView(), BorderLayout.SOUTH);
        
        setSize(600, 800);
        
        model.setBalance(1000);
    }

    public void spinComplete(SymbolModel[][] activeSymbols)
    {
        int[] wins = paylineProcessor.calculateWin(activeSymbols);

        int win = 0;
        for (int i = 0; i < wins.length; i++)
        {
            win += wins[i];
        }

        win *= model.getCreditsPerLine();
//        pnlPaylines.setPays(wins);

        model.setWinAmount(win);
        
        gameStatus.setWin(win);
        doubler.setBalance(win);
    }
    
    private void showDoublingPanel()
    {
        cards.show(pnlCards, "gamble");
        doubler.startDouble();
    }
    
    private void hideDoublingPanel()
    {
        cards.show(pnlCards, "reels");
    }
    
    @Override
    public void spin(int lines, int credits)
    {
        model.setLinesPlayed(lines);
        model.setCreditsPerLine(credits);
        int bet = lines * credits;
        
        if(bet <= model.getBalance())
        {
            model.decreaseBalance(bet);
            
            model.setCreditsPerLine(credits);
            model.setLinesPlayed(lines);
            
            hideDoublingPanel();
            reelController.startSpinning();
        }
    }

    @Override
    public void help()
    {
        System.out.println("Help");
    }

    @Override
    public void gamble()
    {
        showDoublingPanel();
    }

    @Override
    public void takeWin()
    {
        hideDoublingPanel();
        model.increaseBalance(doubler.getBalance());
        doubler.setBalance(0);
    }

    @Override
    public void reset()
    {
        System.out.println("Reset");
        model.increaseBalance(1000);
    }
}