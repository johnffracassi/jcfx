package au.com.barstard.gamestate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameStateController implements GameStateModelListener
{
    @Autowired
    private GameStateModel model;
    
    private GameStateView view;
    
    private int win;
    
    public GameStateController()
    {
        view = new GameStateView();
    }
    
    public GameStateView getView()
    {
        return view;
    }

    private void update()
    {
        view.getLblCreditPerLine().setText(String.format("%d credit(s) per line", model.getCreditsPerLine()));
        view.getLblBet().setText(String.valueOf(model.getLinesPlayed() * model.getCreditsPerLine()));
        view.getLblBetAmount().setText(String.format("$%1.2f", model.getLinesPlayed() * model.getCreditsPerLine() / 100.0));
        view.getLblCredits().setText(String.valueOf(model.getBalance()));
        view.getLblCreditsAmount().setText(String.format("$%1.2f", model.getBalance() / 100.0));
        view.getLblWin().setText(String.valueOf(win));
        view.getLblWinAmount().setText(String.format("$%1.2f", win / 100.0));
    }
    
    public void setWin(int win)
    {
        this.win = win;
        update();
    }

    @Override
    public void gameStateChange(GameStateModel model)
    {
        update();
    }
}
