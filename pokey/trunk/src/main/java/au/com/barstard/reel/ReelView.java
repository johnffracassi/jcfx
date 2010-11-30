package au.com.barstard.reel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import au.com.barstard.VerticalFlowLayout;
import au.com.barstard.symbol.SymbolModel;
import au.com.barstard.symbol.SymbolView;

public class ReelView extends JPanel
{
    private SymbolView pnlSymbol1 = new SymbolView();
    private SymbolView pnlSymbol2 = new SymbolView();
    private SymbolView pnlSymbol3 = new SymbolView();

    public ReelView()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(46, 139, 87));

        JPanel pnlRoot = new JPanel();

        pnlRoot.setBackground(Color.pink);
        pnlRoot.setBorder(BorderFactory.createEtchedBorder());
        pnlRoot.setLayout(new VerticalFlowLayout());

        pnlRoot.add(pnlSymbol1, null);
        pnlRoot.add(pnlSymbol2, null);
        pnlRoot.add(pnlSymbol3, null);
        
        this.setMinimumSize(new Dimension(118, 326));
        this.setPreferredSize(new Dimension(118, 326));

        this.add(pnlRoot, BorderLayout.CENTER);
    }

    public void setSymbol(int index, SymbolModel new_symbol)
    {
        switch (index)
        {
            case 0:
                pnlSymbol1.setSymbol(new_symbol);
                break;
            case 1:
                pnlSymbol2.setSymbol(new_symbol);
                break;
            case 2:
                pnlSymbol3.setSymbol(new_symbol);
                break;
        }
    }
}