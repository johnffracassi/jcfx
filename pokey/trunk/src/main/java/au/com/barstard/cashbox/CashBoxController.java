package au.com.barstard.cashbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Component;

import au.com.barstard.GameEventListener;

@Component
public class CashBoxController implements GameEventListener
{   
    private SimpleDateFormat df = new SimpleDateFormat("hh:mmaa");
    private CashBoxView view;

    private int spins = 0;
    private int cashIn = 0;
    private int cashOut = 0;
    private int creditsPlayed = 0;
    private int creditsPaid = 0;
    private int gambleWinAmount = 0;
    private int gambleLossAmount = 0;
    private int gambleWins = 0;
    private int gambleLosses = 0;
    private int gambleWinMult = 0;
    private int gambleLossMult = 0;
    
    public CashBoxController()
    {
        view = new CashBoxView();
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                view.getLblTime().setText(df.format(new Date()));
            }
        }, 0, 60000);
    }

    public CashBoxView getView()
    {
        return view;
    }

    public void setView(CashBoxView view)
    {
        this.view = view;
    }
    
    public void cashIn(int amount)
    {
        cashIn += amount;
        view.getLblCashIn().setText(String.format("$%1.2f", cashIn / 100.0));
    }
    
    public void cashOut(int amount)
    {
        cashOut += amount;
        view.getLblCashOut().setText(String.format("$%1.2f", cashOut / 100.0));
    }
    
    @Override
    public void spinStarted(int credits, int lines)
    {
        spins ++;
        creditsPlayed += credits * lines;
        view.getLblPaidIn().setText(String.format("$%1.2f", creditsPlayed / 100.0));
        view.getLblSpins().setText(String.format("%d", spins));
    }

    @Override
    public void spinComplete(int amount)
    {
        creditsPaid += amount;
        view.getLblPaidOut().setText(String.format("$%1.2f", creditsPaid / 100.0));
    }

    @Override
    public void takeWin(int amount)
    {
    }

    @Override
    public void gambleWon(int multiplier)
    {
        gambleWins ++;
        view.getLblGambleWins().setText(String.format("%d", gambleWins));
    }

    @Override
    public void gambleLost(int multiplier)
    {
        gambleLosses ++;
        view.getLblGambleLosses().setText(String.format("%d", gambleLosses));
    }

    @Override
    public void gambleComplete(int amount, int multiplier, boolean win)
    {
        if(win)
        {
            gambleWinMult += multiplier;
            gambleWinAmount += amount;
            view.getLblGambleWinAmount().setText(String.format("$%1.2f", gambleWinAmount / 100.0));
            view.getLblGambleWinMult().setText(String.format("%dx", gambleWinMult));
        }
        else
        {
            gambleLossMult += multiplier;
            gambleLossAmount += amount;
            view.getLblGambleLossAmount().setText(String.format("$%1.2f", gambleLossAmount / 100.0));
            view.getLblGambleLossMult().setText(String.format("%dx", gambleLossMult));
        }
    }

    @Override
    public void betChanged(int creditsPerLine, int lines)
    {
    }

    @Override
    public void machineStarted()
    {
    }
}
