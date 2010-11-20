package au.com.barstard.winaction;

import java.util.List;

import au.com.barstard.gamestate.GameStateModel;

public class ChainedWinAction implements WinAction
{
    private List<WinAction> actions;
    
    public ChainedWinAction()
    {
    }
    
    @Override
    public void perform(GameStateModel model)
    {
        for(WinAction action : actions)
        {
            action.perform(model);
        }
    }

    public List<WinAction> getActions()
    {
        return actions;
    }

    public void setActions(List<WinAction> actions)
    {
        this.actions = actions;
    }
}
