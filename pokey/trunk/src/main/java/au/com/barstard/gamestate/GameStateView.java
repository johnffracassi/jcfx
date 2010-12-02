package au.com.barstard.gamestate;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class GameStateView extends JPanel
{
    private JLabel lblCredits;
    private JLabel lblCreditsAmount;
    private JLabel lblBetAmount;
    private JLabel lblBet;
    private JLabel lblWinAmount;
    private JLabel lblWin;
    private JLabel lblCreditPer;
    private JPanel panel_1;

    public GameStateView()
    {
        setOpaque(false);
        setLayout(new MigLayout("", "[33%,fill][33%,grow,fill][33%,fill]", "[17px][17px][17px][17px][]"));
        setPreferredSize(new Dimension(600, 135));
        
        JLabel label = new JLabel("CREDIT");
        label.setFont(new Font("DejaVu Serif Condensed", Font.BOLD | Font.ITALIC, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, "cell 0 0");
        JLabel label_1 = new JLabel("BET");
        label_1.setFont(new Font("DejaVu Serif Condensed", Font.BOLD | Font.ITALIC, 16));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        add(label_1, "cell 1 0");
        JLabel label_2 = new JLabel("WIN");
        label_2.setFont(new Font("DejaVu Serif Condensed", Font.BOLD | Font.ITALIC, 16));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        add(label_2, "cell 2 0");

        lblCredits = new JLabel("1000");
        lblCredits.setFont(new Font("DejaVu Serif Condensed", Font.PLAIN, 14));
        lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCredits, "cell 0 1");

        lblBet = new JLabel("25");
        lblBet.setFont(new Font("DejaVu Serif Condensed", Font.PLAIN, 14));
        lblBet.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblBet, "cell 1 1");

        lblWin = new JLabel("0");
        lblWin.setFont(new Font("DejaVu Serif Condensed", Font.PLAIN, 14));
        lblWin.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblWin, "cell 2 1");

        lblCreditsAmount = new JLabel("$10.00");
        lblCreditsAmount.setFont(new Font("DejaVu Serif Condensed", Font.PLAIN, 14));
        lblCreditsAmount.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCreditsAmount, "cell 0 2");

        lblBetAmount = new JLabel("$0.25");
        lblBetAmount.setFont(new Font("DejaVu Serif Condensed", Font.PLAIN, 14));
        lblBetAmount.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblBetAmount, "cell 1 2");

        lblWinAmount = new JLabel("$0.00");
        lblWinAmount.setFont(new Font("DejaVu Serif Condensed", Font.PLAIN, 14));
        lblWinAmount.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblWinAmount, "cell 2 2");

        JLabel label_6 = new JLabel("$1 = 100 Credits");
        label_6.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 14));
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        add(label_6, "cell 0 3 3 1");

        lblCreditPer = new JLabel("1 credit per line");
        lblCreditPer.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 14));
        lblCreditPer.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCreditPer, "flowy,cell 0 4 3 1");
        
        panel_1 = new JPanel();
        panel_1.setBackground(new Color(46, 139, 87));
        add(panel_1, "cell 0 4");

    }

    public JLabel getLblCredits()
    {
        return lblCredits;
    }

    public JLabel getLblCreditsAmount()
    {
        return lblCreditsAmount;
    }

    public JLabel getLblBetAmount()
    {
        return lblBetAmount;
    }

    public JLabel getLblBet()
    {
        return lblBet;
    }

    public JLabel getLblWinAmount()
    {
        return lblWinAmount;
    }

    public JLabel getLblWin()
    {
        return lblWin;
    }

    public JLabel getLblCreditPerLine()
    {
        return lblCreditPer;
    }
}
