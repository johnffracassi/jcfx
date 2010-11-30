package au.com.barstard.gamestate;

import org.springframework.stereotype.Component;

@Component
public class GameStateModel
{
    private String playerName = "Player";
    private int balance;
    private int creditsPerLine;
    private int linesPlayed;
    private int spinCount;
    
    public int getTotalBet()
    {
        return getCreditsPerLine() * getLinesPlayed();
    }
    
    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
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
    }

    public int getCreditsPerLine()
    {
        return creditsPerLine;
    }

    public void setCreditsPerLine(int creditsPerLine)
    {
        this.creditsPerLine = creditsPerLine;
    }

    public int getLinesPlayed()
    {
        return linesPlayed;
    }

    public void setLinesPlayed(int linesPlayed)
    {
        this.linesPlayed = linesPlayed;
    }

    public int getSpinCount()
    {
        return spinCount;
    }

    public void setSpinCount(int spinCount)
    {
        this.spinCount = spinCount;
    }
}
