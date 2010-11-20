package au.com.barstard.gamestate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameStateModel
{
    @Autowired
    private List<GameStateModelListener> listeners;
    
    private String playerName = "Player";
    private int balance;
    private int creditsPerLine;
    private int linesPlayed;
    private int spinCount;
    private int winAmount;
    private int gambleMultiplier;
    
    public void fireUpdate()
    {
        if(listeners != null)
        {
            for(GameStateModelListener listener : listeners)
            {
                listener.gameStateChange(this);
            }
        }
    }
    
    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
        fireUpdate();
    }

    public int getBalance()
    {
        return balance;
    }

    public void decreaseBalance(int amount)
    {
        setBalance(getBalance() - amount);
    }
    
    public void increaseBalance(int amount)
    {
        setBalance(getBalance() + amount);
    }
    
    public void setBalance(int balance)
    {
        this.balance = balance;
        fireUpdate();
    }

    public int getCreditsPerLine()
    {
        return creditsPerLine;
    }

    public void setCreditsPerLine(int creditsPerLine)
    {
        this.creditsPerLine = creditsPerLine;
        fireUpdate();
    }

    public int getLinesPlayed()
    {
        return linesPlayed;
    }

    public void setLinesPlayed(int linesPlayed)
    {
        this.linesPlayed = linesPlayed;
        fireUpdate();
    }

    public int getSpinCount()
    {
        return spinCount;
    }

    public void setSpinCount(int spinCount)
    {
        this.spinCount = spinCount;
        fireUpdate();
    }

    public int getWinAmount()
    {
        return winAmount;
    }

    public void setWinAmount(int winAmount)
    {
        this.winAmount = winAmount;
        fireUpdate();
    }

    public int getGambleMultiplier()
    {
        return gambleMultiplier;
    }

    public void setGambleMultiplier(int gambleMultiplier)
    {
        this.gambleMultiplier = gambleMultiplier;
        fireUpdate();
    }
}
