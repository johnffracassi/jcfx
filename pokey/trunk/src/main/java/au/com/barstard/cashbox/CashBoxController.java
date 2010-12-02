package au.com.barstard.cashbox;

import java.util.prefs.Preferences;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.barstard.GameEventListener;
import au.com.barstard.gamestate.GameStateModel;

@Component
public class CashBoxController implements GameEventListener
{
    @Autowired
    private GameStateModel model;
    
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
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run()
            {
                collect();
            }
        });
    }

    @PostConstruct
    private void loadValues()
    {
        spins = Preferences.userRoot().getInt("spins", 0);
        cashIn = Preferences.userRoot().getInt("cashIn", 0);
        cashOut = Preferences.userRoot().getInt("cashOut", 0);
        creditsPaid = Preferences.userRoot().getInt("creditsPaid", 0);
        creditsPlayed = Preferences.userRoot().getInt("creditsPlayed", 0);
        gambleWinAmount = Preferences.userRoot().getInt("gambleWinAmount", 0);
        gambleLossAmount = Preferences.userRoot().getInt("gambleLossAmount", 0);
        gambleWins = Preferences.userRoot().getInt("gambleWins", 0);
        gambleLosses = Preferences.userRoot().getInt("gambleLosses", 0);
        gambleWinMult = Preferences.userRoot().getInt("gambleWinMult", 0);
        gambleLossMult = Preferences.userRoot().getInt("gambleLossMult", 0);

        update();
    }

    private void update()
    {
        view.getLblGambleWinAmount().setText(String.format("$%1.2f", gambleWinAmount / 100.0));
        view.getLblGambleWinMult().setText(String.format("%dx", gambleWinMult));
        view.getLblGambleLossAmount().setText(String.format("$%1.2f", gambleLossAmount / 100.0));
        view.getLblGambleLossMult().setText(String.format("%dx", gambleLossMult));
        view.getLblCashIn().setText(String.format("$%1.2f", cashIn / 100.0));
        view.getLblCashOut().setText(String.format("$%1.2f", cashOut / 100.0));
        view.getLblPaidIn().setText(String.format("$%1.2f", creditsPlayed / 100.0));
        view.getLblSpins().setText(String.format("%d", spins));
        view.getLblPaidOut().setText(String.format("$%1.2f", creditsPaid / 100.0));
        view.getLblGambleWins().setText(String.format("%d", gambleWins));
        view.getLblGambleLosses().setText(String.format("%d", gambleLosses));
    }

    private void resetAllValues()
    {
        Preferences.userRoot().putInt("spins", 0);
        Preferences.userRoot().putInt("cashIn", 0);
        Preferences.userRoot().putInt("cashOut", 0);
        Preferences.userRoot().putInt("creditsPaid", 0);
        Preferences.userRoot().putInt("creditsPlayed", 0);
        Preferences.userRoot().putInt("gambleWinAmount", 0);
        Preferences.userRoot().putInt("gambleLossAmount", 0);
        Preferences.userRoot().putInt("gambleWins", 0);
        Preferences.userRoot().putInt("gambleLosses", 0);
        Preferences.userRoot().putInt("gambleWinMult", 0);
        Preferences.userRoot().putInt("gambleLossMult", 0);
    }
    
    private void collect()
    {
        cashOut += model.getBalance();
        Preferences.userRoot().putInt("cashOut", cashOut);
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
        Preferences.userRoot().putInt("cashIn", cashIn);
        update();
    }

    public void cashOut(int amount)
    {
        cashOut += amount;
        Preferences.userRoot().putInt("cashOut", cashOut);
        update();
    }

    @Override
    public void spinStarted(int credits, int lines)
    {
        spins++;
        creditsPlayed += credits * lines;
        Preferences.userRoot().putInt("spins", spins);
        Preferences.userRoot().putInt("creditsPlayed", creditsPlayed);
        update();
    }

    @Override
    public void spinComplete(int amount)
    {
        creditsPaid += amount;
        Preferences.userRoot().putInt("creditsPaid", creditsPaid);
        update();
    }

    @Override
    public void takeWin(int amount)
    {}

    @Override
    public void gambleWon(int multiplier)
    {
        gambleWins++;
        Preferences.userRoot().putInt("gambleWins", gambleWins);
        update();
    }

    @Override
    public void gambleLost(int multiplier)
    {
        gambleLosses++;
        Preferences.userRoot().putInt("gambleLosses", gambleLosses);
        update();
    }

    @Override
    public void gambleComplete(int amount, int multiplier, boolean win)
    {
        if (win)
        {
            gambleWinMult += multiplier;
            gambleWinAmount += amount;
            Preferences.userRoot().putInt("gambleWinMult", gambleWinMult);
            Preferences.userRoot().putInt("gambleWinAmount", gambleWinAmount);
        }
        else
        {
            gambleLossMult += multiplier;
            gambleLossAmount += amount;
            Preferences.userRoot().putInt("gambleLossMult", gambleLossMult);
            Preferences.userRoot().putInt("gambleLossAmount", gambleLossAmount);
        }
        update();
    }

    @Override
    public void betChanged(int creditsPerLine, int lines)
    {}

    @Override
    public void machineStarted()
    {}
}
