package au.com.barstard;

public class GameEventAdapter implements GameEventListener
{
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
    }

    @Override
    public void takeWin(int amount)
    {
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
    }

    @Override
    public void betChanged(int creditsPerLine, int lines)
    {
    }
}

