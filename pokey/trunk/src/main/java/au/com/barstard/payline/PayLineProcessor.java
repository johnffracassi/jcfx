package au.com.barstard.payline;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import au.com.barstard.gamestate.GameStateModel;
import au.com.barstard.symbol.SymbolModel;
import au.com.barstard.win.ChainedWinAction;
import au.com.barstard.win.IncreaseBalanceWinAction;
import au.com.barstard.win.WinAction;
import au.com.barstard.win.WinDefinition;

public class PayLineProcessor
{
    @Autowired
    private GameStateModel model;
    
    private PayLine[] payLines;

    private List<String> payLineDefinitions;
    
    @Autowired
    private List<WinDefinition> winDefinitions;

    public static void main(String[] args)
    {
        PayLineProcessor plp = new PayLineProcessor();
        
        List<WinDefinition> defs = new LinkedList<WinDefinition>();
        WinDefinition def1 = new WinDefinition();
        def1.setPattern("[9#][9#]...");
        defs.add(def1);
        WinDefinition def2 = new WinDefinition();
        def2.setPattern("[K#][K#][K#][K#].");
        defs.add(def2);
        
        plp.setPayLineDefinitions(Arrays.asList(new String[] { "AAAAA", "AABCC", "CBABC" }));
        plp.setWinDefinitions(defs);
        
        plp.calculateWin("99KA9");
        plp.calculateWin("K9KA9");
        plp.calculateWin("##KK9");
    }
    
    @PostConstruct
    private void init()
    {
        payLines = new PayLine[payLineDefinitions.size()];
        for(int i=0; i<payLineDefinitions.size(); i++)
        {
            payLines[i] = new PayLine(payLineDefinitions.get(i));
        }
    }
    
    private SymbolModel[] getLine(SymbolModel[][] activeSymbols, int idx)
    {
        String lineStr = payLineDefinitions.get(idx);
        
        SymbolModel[] symbols = new SymbolModel[lineStr.length()];
        for(int r=0; r<lineStr.length(); r++)
        {
            int pos = lineStr.charAt(r) - 'A';
            symbols[r] = activeSymbols[r][pos];
        }
        return symbols;
    }

    public int[] calculateWin(SymbolModel[][] activeSymbols)
    {
        int[] wins = new int[model.getLinesPlayed()];
        
        for(int l=0; l<model.getLinesPlayed(); l++)
        {
            SymbolModel[] symbols = getLine(activeSymbols, l);
            String symbolsStr = toString(symbols);
            wins[l] = calculateWin(symbolsStr);
        }

        return wins;
    }

    private int calculateWin(String symbols)
    {
        int bestWinAmount = 0;
        
        for(WinDefinition def : winDefinitions)
        {
            if(def.matches(symbols))
            {
                WinAction action = def.getAction();
                int win = findBalanceIncrease(action);
                if(win > bestWinAmount)
                {
                    bestWinAmount = win;
                }
            }
        }
        
        return bestWinAmount;
    }

    private int findBalanceIncrease(WinAction action)
    {
        if(action instanceof IncreaseBalanceWinAction)
        {
            IncreaseBalanceWinAction a = (IncreaseBalanceWinAction)action;
            return a.getAmount();
        }
        else if(action instanceof ChainedWinAction)
        {
            for(WinAction chainedAction : ((ChainedWinAction)action).getActions())
            {
                if(chainedAction instanceof IncreaseBalanceWinAction)
                {
                    return findBalanceIncrease(chainedAction);
                }
            }
        }
        return 0;
    }
    
    private String toString(SymbolModel[] symbols)
    {
        String str = "";
        for(SymbolModel sym : symbols)
        {
            str += sym.getLabel();
        }
        return str;
    }
    
    public List<String> getPayLineDefinitions()
    {
        return payLineDefinitions;
    }

    public void setPayLineDefinitions(List<String> payLineDefinitions)
    {
        this.payLineDefinitions = payLineDefinitions;
    }

    public void setWinDefinitions(List<WinDefinition> winDefinitions)
    {
        this.winDefinitions = winDefinitions;
    }

    public List<WinDefinition> getWinDefinitions()
    {
        return winDefinitions;
    }
}