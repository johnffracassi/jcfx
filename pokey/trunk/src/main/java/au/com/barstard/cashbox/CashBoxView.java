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
        setLayout(new MigLayout("", "[10%,fill][10%,fill][10%,fill][10%,fill][10%,fill][4%,fill][6%,fill][8%,fill][4%,fill][6%,fill][8%,fill]", "[15px][15px]"));
        setOpaque(false);
        setPreferredSize(new Dimension(593, 50));

        lblSpinsLabel = new JLabel("#");
        lblSpinsLabel.setForeground(new Color(95, 158, 160));
        lblSpinsLabel.setOpaque(true);
        lblSpinsLabel.setBackground(new Color(47, 79, 79));
        lblSpinsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblSpinsLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblSpinsLabel, "flowx,cell 0 0");

        JLabel lblCashInLabel = new JLabel("CI");
        lblCashInLabel.setForeground(new Color(95, 158, 160));
        lblCashInLabel.setOpaque(true);
        lblCashInLabel.setBackground(new Color(47, 79, 79));
        lblCashInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashInLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblCashInLabel, "cell 1 0,growx");

        JLabel lblCashOutLabel = new JLabel("CO");
        lblCashOutLabel.setForeground(new Color(95, 158, 160));
        lblCashOutLabel.setOpaque(true);
        lblCashOutLabel.setBackground(new Color(47, 79, 79));
        lblCashOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashOutLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblCashOutLabel, "cell 2 0");

        JLabel lblPaidInLabel = new JLabel("PI");
        lblPaidInLabel.setForeground(new Color(95, 158, 160));
        lblPaidInLabel.setOpaque(true);
        lblPaidInLabel.setBackground(new Color(47, 79, 79));
        lblPaidInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidInLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblPaidInLabel, "cell 3 0");

        JLabel lblPaidOutLabel = new JLabel("PO");
        lblPaidOutLabel.setForeground(new Color(95, 158, 160));
        lblPaidOutLabel.setOpaque(true);
        lblPaidOutLabel.setBackground(new Color(47, 79, 79));
        lblPaidOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidOutLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblPaidOutLabel, "cell 4 0");

        lblGambleWinsLabel = new JLabel("Gambling Wins");
        lblGambleWinsLabel.setForeground(new Color(95, 158, 160));
        lblGambleWinsLabel.setOpaque(true);
        lblGambleWinsLabel.setBackground(new Color(47, 79, 79));
        lblGambleWinsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWinsLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblGambleWinsLabel, "cell 5 0 3 1,alignx center");

        lblGambleLossesLabel = new JLabel("Gamble Losses");
        lblGambleLossesLabel.setForeground(new Color(95, 158, 160));
        lblGambleLossesLabel.setOpaque(true);
        lblGambleLossesLabel.setBackground(new Color(47, 79, 79));
        lblGambleLossesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLossesLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        add(lblGambleLossesLabel, "cell 8 0 3 1");

        lblSpins = new JLabel("0");
        lblSpins.setHorizontalAlignment(SwingConstants.CENTER);
        lblSpins.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblSpins, "flowx,cell 0 1");

        lblCashIn = new JLabel("$0.00");
        lblCashIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashIn.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblCashIn, "cell 1 1,alignx center");

        lblCashOut = new JLabel("$0.00");
        lblCashOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashOut.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblCashOut, "cell 2 1");

        lblPaidIn = new JLabel("$0.00");
        lblPaidIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidIn.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblPaidIn, "cell 3 1");

        lblPaidOut = new JLabel("$0.00");
        lblPaidOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidOut.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblPaidOut, "cell 4 1");

        lblGambleWins = new JLabel("0");
        lblGambleWins.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWins.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblGambleWins, "cell 5 1,growx");

        lblGambleWinMult = new JLabel("0x");
        lblGambleWinMult.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWinMult.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblGambleWinMult, "cell 6 1,alignx center");

        lblGambleWinAmount = new JLabel("$0.00");
        lblGambleWinAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleWinAmount.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblGambleWinAmount, "cell 7 1,alignx center");

        lblGambleLosses = new JLabel("0");
        lblGambleLosses.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLosses.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblGambleLosses, "cell 8 1");

        lblGambleLossMult = new JLabel("0x");
        lblGambleLossMult.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLossMult.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblGambleLossMult, "cell 9 1");

        lblGambleLossAmount = new JLabel("$0.00");
        lblGambleLossAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambleLossAmount.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
        add(lblGambleLossAmount, "cell 10 1");

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
