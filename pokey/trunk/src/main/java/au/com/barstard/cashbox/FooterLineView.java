package au.com.barstard.cashbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FooterLineView extends JPanel
{
    private JLabel lblTime;
    private JLabel lblCopyright;
    private JLabel lblDenomination;

    /**
     * Create the panel.
     */
    public FooterLineView()
    {
        setOpaque(false);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(600, 30));
        
        lblTime = new JLabel("11:52pm");
        lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime.setPreferredSize(new Dimension(100, 15));
        lblTime.setFont(new Font("DejaVu Serif Condensed", Font.ITALIC, 20));
        add(lblTime, BorderLayout.WEST);
        
        lblCopyright = new JLabel("(c)2010 Siebentag Poker Machine Corporation Ltd.");
        lblCopyright.setFont(new Font("DejaVu Serif Condensed", Font.BOLD | Font.ITALIC, 12));
        lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCopyright, BorderLayout.CENTER);
        
        lblDenomination = new JLabel("1c");
        lblDenomination.setHorizontalAlignment(SwingConstants.CENTER);
        lblDenomination.setPreferredSize(new Dimension(100, 15));
        lblDenomination.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 24));
        add(lblDenomination, BorderLayout.EAST);

    }

    public JLabel getLblTime() {
        return lblTime;
    }
    public JLabel getLblCopyright() {
        return lblCopyright;
    }
    public JLabel getLblDenomination() {
        return lblDenomination;
    }
}
