package au.com.barstard.gamestate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.barstard.GameEventAdapter;

@Component
public class GameStateController extends GameEventAdapter
{
    @Autowired
    private GameStateModel gameState;
    
    private GameStateView view;
    
    public GameStateController()
    {
        view = new GameStateView();
    }
    
    public GameStateView getView()
    {
        return view;
    }

    @Override
    public void spinStarted(int credits, int lines)
    {
        view.getLblCredits().setText(String.valueOf(gameState.getBalance()));
        view.getLblCreditsAmount().setText(String.format("$%1.2f", gameState.getBalance() / 100.0));
    }
    
    @Override
    public void spinComplete(int win)
    {
        view.getLblWin().setText(String.valueOf(win));
        view.getLblWinAmount().setText(String.format("$%1.2f", win / 100.0));
    }
    
    @Override
    public void takeWin(int amount)
    {
        view.getLblCredits().setText(String.valueOf(gameState.getBalance()));
        view.getLblCreditsAmount().setText(String.format("$%1.2f", gameState.getBalance() / 100.0));
    }
    
    @Override
    public void betChanged(int creditsPerLine, int lines)
    {
        view.getLblCreditPerLine().setText(String.format("%d credit(s) per line", creditsPerLine));
        view.getLblBet().setText(String.valueOf(lines * creditsPerLine));
        view.getLblBetAmount().setText(String.format("$%1.2f", lines * creditsPerLine / 100.0));
    }
}
