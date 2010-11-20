package au.com.barstard.winaction;

import au.com.barstard.gamestate.GameStateModel;

public class IncreaseBalanceWinAction implements WinAction
{
    private int amount;
    
    public IncreaseBalanceWinAction()
    {
    }
    
    @Override
    public void perform(GameStateModel model)
    {
        model.increaseBalance(amount);
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }
}
