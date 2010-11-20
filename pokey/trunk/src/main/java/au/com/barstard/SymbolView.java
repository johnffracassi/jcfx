package au.com.barstard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SymbolView extends JPanel
{
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel lblDisplay = new JLabel();

    public void setSymbol(SymbolModel symbol)
    {
        lblDisplay.setText(symbol.getDisplay());
    }

    public SymbolView()
    {
        lblDisplay.setFont(new java.awt.Font("SansSerif", 1, 48));
        lblDisplay.setBorder(null);
        lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        lblDisplay.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDisplay.setText("A");
        
        setBackground(new Color(46, 139, 87));
        setPreferredSize(new Dimension(100, 100));
        setLayout(borderLayout1);
        add(lblDisplay, BorderLayout.CENTER);
        
        JLabel lblAce = new JLabel("Ace");
        lblAce.setFont(new Font("Dialog", Font.PLAIN, 9));
        lblAce.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblAce, BorderLayout.SOUTH);
    }
}