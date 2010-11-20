package au.com.barstard;

import java.util.regex.Pattern;

import au.com.barstard.winaction.WinAction;

public class WinDefinition
{
    private String pattern;
    private WinAction action;

    public boolean matches(String symbols)
    {
        return Pattern.matches(pattern, symbols);
    }
    
    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        String newPattern = "";
        for(int i=0; i<pattern.length(); i++)
        {
            if(pattern.charAt(i) == '.' || pattern.charAt(i) == '#')
            {
                newPattern += pattern.charAt(i);
            }
            else
            {
                newPattern += "[" + pattern.charAt(i) + "#]";
            }
        }
        
        this.pattern = newPattern;
    }

    public WinAction getAction()
    {
        return action;
    }

    public void setAction(WinAction action)
    {
        this.action = action;
    }

}
