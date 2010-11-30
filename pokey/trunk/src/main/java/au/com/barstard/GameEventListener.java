package au.com.barstard;

public interface GameEventListener
{
    void machineStarted();
    void cashIn(int amount);
    void cashOut(int amount);
    void spinStarted(int credits, int lines);
    void spinComplete(int winAmount);
    void takeWin(int amount);
    void gambleWon(int multiplier);
    void gambleLost(int multiplier);
    void gambleComplete(int amount, int multiplier, boolean win);
    void betChanged(int creditsPerLine, int lines);
}

