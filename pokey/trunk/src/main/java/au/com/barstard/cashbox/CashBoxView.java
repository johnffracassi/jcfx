package au.com.barstard.cashbox;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class CashBoxView extends JPanel
{
    private JLabel lblRecordName;
    private JLabel lblRecordValue;
    private JLabel lblTime;
    private JLabel lblCashIn;
    private JLabel lblCashOut;
    private JLabel lblPaidIn;
    private JLabel lblPaidOut;
    private JLabel lblGambleWinsLabel;
    private JLabel lblGambleLossesLabel;
    private JLabel lblGambleWins;
    private JLabel lblGambleWinAmount;
    private JLabel lblGambleLosses;
    private JLabel lblGambleLossAmount;
    private JLabel lblGambleWinMult;
    private JLabel lblGambleLossMult;
    private JLabel lblSpins;
    private JLabel lblSpinsLabel;

    /**
     * Create the panel.
     */
    public CashBoxView()
    {
        setLayout(new MigLayout("",
                "[8%,fill][][8%,fill][8%,fill][8%,fill][4%,fill][6%,fill][8%,fill][4%,fill][6%,fill][8%,fill][grow,fill]",
                "[20%][40%][grow][15px][15px]"));
        setOpaque(false);
        setPreferredSize(new Dimension(600, 149));

        lblRecordName = new JLabel("Record Name");
        lblRecordName.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblRecordName, "cell 0 0 13 1,growx");

        lblRecordValue = new JLabel("Record Value");
        lblRecordValue.setFont(new Font("Dialog", Font.BOLD, 18));
        lblRecordValue.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblRecordValue, "cell 0 1 13 1,growx");

        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        add(panel, "cell 0 2 12 1,grow");

        lblSpinsLabel = new JLabel("Spins");
        lblSpinsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblSpinsLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblSpinsLabel, "flowx,cell 0 3");

        JLabel lblCashInLabel = new JLabel("Cash In");
        lblCashInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashInLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblCashInLabel, "cell 1 3");

        JLabel lblCashOutLabel = new JLabel("Cash Out");
        lblCashOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashOutLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblCashOutLabel, "cell 2 3");

        JLabel lblPaidInLabel = new JLabel("Paid In");
        lblPaidInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidInLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblPaidInLabel, "cell 3 3");

        JLabel lblPaidOutLabel = new JLabel("Paid Out");
        lblPaidOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidOutLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblPaidOutLabel, "cell 4 3");

        lblGambleWinsLabel = new JLabel("Gamble Wins");
        lblGambleWinsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWinsLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblGambleWinsLabel, "cell 5 3 3 1");

        lblGambleLossesLabel = new JLabel("Gamble Losses");
        lblGambleLossesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLossesLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
        add(lblGambleLossesLabel, "cell 8 3 3 1");

        lblTime = new JLabel("7:02pm");
        lblTime.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 14));
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblTime, "cell 11 3 1 2,grow");

        lblSpins = new JLabel("0");
        lblSpins.setHorizontalAlignment(SwingConstants.CENTER);
        lblSpins.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblSpins, "flowx,cell 0 4");

        lblCashIn = new JLabel("$0.00");
        lblCashIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashIn.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblCashIn, "cell 1 4");

        lblCashOut = new JLabel("$0.00");
        lblCashOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashOut.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblCashOut, "cell 2 4");

        lblPaidIn = new JLabel("$0.00");
        lblPaidIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidIn.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblPaidIn, "cell 3 4");

        lblPaidOut = new JLabel("$0.00");
        lblPaidOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidOut.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblPaidOut, "cell 4 4");

        lblGambleWins = new JLabel("0");
        lblGambleWins.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWins.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblGambleWins, "cell 5 4");

        lblGambleWinMult = new JLabel("0x");
        lblGambleWinMult.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWinMult.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblGambleWinMult, "cell 6 4");

        lblGambleWinAmount = new JLabel("$0.00");
        lblGambleWinAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWinAmount.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblGambleWinAmount, "cell 7 4");

        lblGambleLosses = new JLabel("0");
        lblGambleLosses.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLosses.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblGambleLosses, "cell 8 4");

        lblGambleLossMult = new JLabel("0x");
        lblGambleLossMult.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLossMult.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblGambleLossMult, "cell 9 4");

        lblGambleLossAmount = new JLabel("$0.00");
        lblGambleLossAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLossAmount.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
        add(lblGambleLossAmount, "cell 10 4");

    }

    public JLabel getLblRecordName()
    {
        return lblRecordName;
    }

    public JLabel getLblRecordValue()
    {
        return lblRecordValue;
    }

    public JLabel getLblTime()
    {
        return lblTime;
    }

    public JLabel getLblCashIn()
    {
        return lblCashIn;
    }

    public JLabel getLblCashOut()
    {
        return lblCashOut;
    }

    public JLabel getLblPaidIn()
    {
        return lblPaidIn;
    }

    public JLabel getLblPaidOut()
    {
        return lblPaidOut;
    }

    public JLabel getLblGambleLossAmount()
    {
        return lblGambleLossAmount;
    }

    public JLabel getLblGambleLosses()
    {
        return lblGambleLosses;
    }

    public JLabel getLblGambleWinAmount()
    {
        return lblGambleWinAmount;
    }

    public JLabel getLblGambleWins()
    {
        return lblGambleWins;
    }

    public JLabel getLblGambleWinMult()
    {
        return lblGambleWinMult;
    }

    public JLabel getLblGambleLossMult()
    {
        return lblGambleLossMult;
    }
    public JLabel getLblSpins() {
        return lblSpins;
    }
}
