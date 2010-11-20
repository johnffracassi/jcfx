package au.com.barstard.doubler;

import java.awt.Color;

public enum Suit
{
    Spade("S", Colour.Black), 
    Club("C", Colour.Black), 
    Heart("H", Colour.Red), 
    Diamond("D", Colour.Red);
    
    private String label;
    private Colour colour;
    
    Suit(String label, Colour colour)
    {
        this.label = label;
        this.colour = colour;
    }
    
    public static Suit random()
    {
        return values()[(int)(Math.random() * values().length)];
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public Colour getColour()
    {
        return colour;
    }
    
    public Color getDisplayColor()
    {
        return colour.getColor();
    }
}
