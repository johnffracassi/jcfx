package au.com.barstard.blokey.records;

import org.springframework.stereotype.Component;

@Component
public class RecordKeeper 
{
    public RecordKeeper()
    {
        
    }
    
    public void doubleResult(boolean won, int multiplier, int balance)
    {
        System.out.println((won?"won":"lost") + " double worth " + balance + " / " + multiplier + "x");
    }
}
