package au.com.barstard;

/**
 * Title:        Pokey Simulator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      MBlue Pty Ltd
 * @author Jeffrey Cann
 * @version 1.0
 */

public class Line
{
    private SymbolModel[] symbols = null;

    public Line(int size)
    {
        symbols = new SymbolModel[size];

        for (int i = 0; i < size; i++)
        {
            symbols[i] = null;
        }
    }

    public Line(SymbolModel[] symbols)
    {
        this.symbols = symbols;
    }

    public void setSymbol(int reel, SymbolModel symbol)
    {
        symbols[reel] = symbol;
    }

    public SymbolModel getSymbol(int reel)
    {
        return symbols[reel];
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < symbols.length; i++)
        {
            buf.append(symbols[i].getDisplay() + " ");
        }

        return buf.toString();
    }
}