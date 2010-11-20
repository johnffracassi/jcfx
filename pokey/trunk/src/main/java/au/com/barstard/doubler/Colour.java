package au.com.barstard.doubler;

import java.awt.Color;

public enum Colour
{
    Red(Color.RED), 
    Black(Color.BLACK);
    
    private Color color;
    
    Colour(Color col)
    {
        this.color = col;
    }
    
    public Color getColor()
    {
        return color;
    }
}
