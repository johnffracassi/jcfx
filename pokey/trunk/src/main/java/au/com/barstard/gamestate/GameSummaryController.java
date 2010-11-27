package au.com.barstard.gamestate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Component;

@Component
public class GameSummaryController
{   
    private SimpleDateFormat df = new SimpleDateFormat("hh:mmaa");
    private GameSummaryView view;

    private int cashIn = 0;
    private int cashOut = 0;
    private int paidIn = 0;
    private int paidOut = 0;
    
    public GameSummaryController()
    {
        view = new GameSummaryView();
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                view.getLblTime().setText(df.format(new Date()));
            }
        }, 0, 60000);
    }

    public GameSummaryView getView()
    {
        return view;
    }

    public void setView(GameSummaryView view)
    {
        this.view = view;
    }
    
    public void paidIn(int amount)
    {
        paidIn += amount;
        view.getLblPaidIn().setText(String.format("$%1.2f", paidIn / 100.0));
    }
    
    public void paidOut(int amount)
    {
        paidOut += amount;
        view.getLblPaidOut().setText(String.format("$%1.2f", paidOut / 100.0));
    }
}
