package au.com.barstard.winaction;

import au.com.barstard.gamestate.GameStateModel;

public class LogWinAction implements WinAction
{
    public LogWinAction()
    {
    }
    
    @Override
    public void perform(GameStateModel model)
    {
        System.out.println("win");
    }
}
