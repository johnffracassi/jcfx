package au.com.barstard;

/**
 * Title:        Pokey Simulator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      MBlue Pty Ltd
 * @author Jeffrey Cann
 * @version 1.0
 */

public class PayLine
{
    private int[] offsets = null;

    public PayLine(String offset)
    {
        offsets = new int[5];
        
        for(int i=0; i<5; i++)
        {
            offsets[i] = offset.charAt(i) - 'B';
        }
    }

    public Line getLine(SymbolModel[][] activeSymbols)
    {
        Line line = new Line(activeSymbols.length);

        for (int i = 0; i < offsets.length; i++)
        {
            line.setSymbol(i, activeSymbols[i][offsets[i] + 1]);
        }

        return line;
    }
}