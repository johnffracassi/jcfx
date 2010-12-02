package au.com.barstard.cashbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Component;

@Component
public class FooterLineController
{
    private FooterLineView view;

    public FooterLineController()
    {
        view = new FooterLineView();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private SimpleDateFormat df = new SimpleDateFormat("h:mmaa");

            @Override
            public void run()
            {
                view.getLblTime().setText(df.format(new Date()));
            }
        }, 0, 60000);
    }

    public FooterLineView getView()
    {
        return view;
    }
}
