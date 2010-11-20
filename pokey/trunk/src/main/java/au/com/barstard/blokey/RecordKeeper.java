package au.com.barstard.blokey;

import org.springframework.stereotype.Component;

@Component
public class RecordKeeper 
{
    public void doubleResult(boolean won, int multiplier, int balance)
    {
        System.out.println((won?"won":"lost") + " double worth " + balance + " / " + multiplier + "x");
    }
}
