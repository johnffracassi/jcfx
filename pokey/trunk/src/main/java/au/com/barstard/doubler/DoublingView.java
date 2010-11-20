package au.com.barstard.doubler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;


public class DoublingView extends JPanel
{
    private JLabel lblRiskPayout;
    private JButton btnSpades;
    private JButton btnClubs;
    private JButton btnHearts;
    private JButton btnDiamonds;
    private JButton btnBlack;
    private JButton btnRed;
    private HistoryPanel pnlHistory;

    public DoublingView()
    {
        setLayout(new MigLayout("", "[5%][20%][20%,grow][10%][20%][20%][5%]", "[15%][15%][25%][25%,grow][20%]"));
        setBackground(new Color(46, 139, 87));

        btnSpades = formatButton(new JButton("S"), Color.black);
        btnClubs = formatButton(new JButton("C"), Color.black);
        btnHearts = formatButton(new JButton("H"), Color.red);
        btnDiamonds = formatButton(new JButton("D"), Color.red);
        btnBlack = formatButton(new JButton("B"), Color.black);
        btnRed = formatButton(new JButton("R"), Color.red);
        
        add(btnHearts, "cell 1 2,grow");
        add(btnDiamonds, "cell 2 2,grow");
        add(btnRed, "cell 1 3,grow");
        add(btnClubs, "cell 4 2,grow");
        add(btnSpades, "cell 5 2,grow");
        add(btnBlack, "cell 5 3,grow");
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 0, 255));
        add(panel, "cell 0 0 7 1,grow");
        
        JLabel lblBalanceMessages = new JLabel("Balance / Messages");
        panel.add(lblBalanceMessages);
        
        pnlHistory = new HistoryPanel();
        add(pnlHistory, "cell 1 1 5 1,grow");
        
        JPanel panel_9 = new JPanel();
        panel_9.setBackground(new Color(255, 215, 0));
        add(panel_9, "cell 2 3 3 1,grow");
        panel_9.setLayout(new BorderLayout(0, 0));
        
        lblRiskPayout = new JLabel("Risk / Payout");
        lblRiskPayout.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 30));
        lblRiskPayout.setHorizontalAlignment(SwingConstants.CENTER);
        panel_9.add(lblRiskPayout);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBackground(new Color(0, 139, 139));
        add(panel_8, "cell 0 4 7 1,grow");
        
        JLabel lblSomeOtherInfo = new JLabel("Some other info");
        panel_8.add(lblSomeOtherInfo);
        
        setPreferredSize(new Dimension(510, 310));

    }

    private JButton formatButton(JButton btn, Color color)
    {
        btn.setFont(new Font("Dialog", Font.BOLD, 36));
        btn.setForeground(new Color(255, 255, 255));
        btn.setBackground(color);
        return btn;
    }
    
    public JLabel getLblRiskPayout() {
        return lblRiskPayout;
    }

    public JButton getBtnSpades()
    {
        return btnSpades;
    }

    public JButton getBtnClubs()
    {
        return btnClubs;
    }

    public JButton getBtnHearts()
    {
        return btnHearts;
    }

    public JButton getBtnDiamonds()
    {
        return btnDiamonds;
    }

    public JButton getBtnBlack()
    {
        return btnBlack;
    }

    public JButton getBtnRed()
    {
        return btnRed;
    }
    public HistoryPanel getPnlHistory() {
        return pnlHistory;
    }
}
