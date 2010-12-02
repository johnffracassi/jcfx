package au.com.barstard.records;

import org.springframework.stereotype.Component;

@Component
public class RecordsController
{
    private RecordsView view;
    
    public RecordsController()
    {
        this.view = new RecordsView(3);
    }
    
    public RecordsView getView()
    {
        return view;
    }
}
