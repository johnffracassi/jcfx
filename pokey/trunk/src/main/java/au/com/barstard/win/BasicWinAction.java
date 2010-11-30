package au.com.barstard.win;

import java.util.ArrayList;
import java.util.List;

public class BasicWinAction extends ChainedWinAction
{
    public BasicWinAction(int amount)
    {
        IncreaseBalanceWinAction win = new IncreaseBalanceWinAction();
        win.setAmount(amount);
        
        LogWinAction log = new LogWinAction();
        
        List<WinAction> actions = new ArrayList<WinAction>();
        actions.add(win);
        actions.add(log);
        setActions(actions);
    }
}
