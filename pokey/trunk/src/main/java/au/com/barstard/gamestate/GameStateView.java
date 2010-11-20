package au.com.barstard.gamestate;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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

    public GameStateView()
    {
        setBackground(new Color(46, 139, 87));
        setLayout(new MigLayout("", "[33%,fill][33%,fill][33%,fill]", "[][][][][]"));

        JLabel label = new JLabel("CREDIT");
        label.setFont(new Font("Arial Black", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, "cell 0 0");
        JLabel label_1 = new JLabel("BET");
        label_1.setFont(new Font("Arial Black", Font.BOLD, 14));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        add(label_1, "cell 1 0");
        JLabel label_2 = new JLabel("WIN");
        label_2.setFont(new Font("Arial Black", Font.BOLD, 14));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        add(label_2, "cell 2 0");

        lblCredits = new JLabel("1000");
        lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCredits, "cell 0 1");

        lblBet = new JLabel("25");
        lblBet.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblBet, "cell 1 1");

        lblWin = new JLabel("0");
        lblWin.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblWin, "cell 2 1");

        lblCreditsAmount = new JLabel("$10.00");
        lblCreditsAmount.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCreditsAmount, "cell 0 2");

        lblBetAmount = new JLabel("$0.25");
        lblBetAmount.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblBetAmount, "cell 1 2");

        lblWinAmount = new JLabel("$0.00");
        lblWinAmount.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblWinAmount, "cell 2 2");

        JLabel label_6 = new JLabel("$1 = 100 Credits");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        add(label_6, "cell 0 3 3 1");

        lblCreditPer = new JLabel("1 credit per line");
        lblCreditPer.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCreditPer, "cell 0 4 3 1");

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
