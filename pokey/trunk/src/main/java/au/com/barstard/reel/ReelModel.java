package au.com.barstard.reel;

import java.util.*;

import au.com.barstard.symbol.SymbolModel;


public class ReelModel
{
    private List<SymbolModel> symbols;
    private int position = 0;

    public ReelModel()
    {
    }
    
    public void rotate(int steps)
    {
        position = (position + steps) % symbols.size();
    }

    public SymbolModel[] getActiveSymbols()
    {
        SymbolModel[] s = new SymbolModel[3];

        s[0] = getActiveSymbol(-1);
        s[1] = getActiveSymbol(0);
        s[2] = getActiveSymbol(1);

        return s;
    }

    private SymbolModel getActiveSymbol(int offset)
    {
        int index = (position + offset + symbols.size()) % symbols.size();
        return getSymbolAt(index);
    }

    private SymbolModel getSymbolAt(int index)
    {
        return (SymbolModel) symbols.get(index);
    }
    
    public List<SymbolModel> getSymbols()
    {
        return symbols;
    }
    
    public int size()
    {
        return symbols.size();
    }

    public void setSymbols(List<SymbolModel> symbols)
    {
        this.symbols = symbols;
        position = (int)(Math.random() * symbols.size());
    }
}