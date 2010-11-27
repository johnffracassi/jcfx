package au.com.barstard.gamestate;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class GameSummaryView extends JPanel
{
    private JLabel lblRecordName;
    private JLabel lblRecordValue;
    private JLabel lblTime;
    private JLabel lblCashIn;
    private JLabel lblCashOut;
    private JLabel lblPaidIn;
    private JLabel lblPaidOut;

    /**
     * Create the panel.
     */
    public GameSummaryView()
    {
        setLayout(new MigLayout("", "[10%,fill][10%,fill][10%,fill][10%,fill][fill,grow]", "[20%][40%][grow][15px][15px]"));
        setOpaque(false);
        setPreferredSize(new Dimension(600, 149));

        lblRecordName = new JLabel("Record Name");
        lblRecordName.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblRecordName, "cell 0 0 6 1,growx");

        lblRecordValue = new JLabel("Record Value");
        lblRecordValue.setFont(new Font("Dialog", Font.BOLD, 18));
        lblRecordValue.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblRecordValue, "cell 0 1 6 1,growx");

        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        add(panel, "cell 0 2 5 1,grow");

        JLabel lblCashInLabel = new JLabel("Cash In");
        lblCashInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashInLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 9));
        add(lblCashInLabel, "cell 0 3");

        JLabel lblCashOutLabel = new JLabel("Cash Out");
        lblCashOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashOutLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 9));
        add(lblCashOutLabel, "cell 1 3");

        JLabel lblPaidInLabel = new JLabel("Paid In");
        lblPaidInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidInLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 9));
        add(lblPaidInLabel, "cell 2 3");

        JLabel lblPaidOutLabel = new JLabel("Paid Out");
        lblPaidOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidOutLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 9));
        add(lblPaidOutLabel, "cell 3 3");

        lblTime = new JLabel("7:02pm");
        lblTime.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 14));
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblTime, "cell 4 3 1 2,grow");

        lblCashIn = new JLabel("$0.00");
        lblCashIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashIn.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(lblCashIn, "cell 0 4");

        lblCashOut = new JLabel("$0.00");
        lblCashOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashOut.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(lblCashOut, "cell 1 4");

        lblPaidIn = new JLabel("$0.00");
        lblPaidIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidIn.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(lblPaidIn, "cell 2 4");

        lblPaidOut = new JLabel("$0.00");
        lblPaidOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblPaidOut.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(lblPaidOut, "cell 3 4");

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
}
