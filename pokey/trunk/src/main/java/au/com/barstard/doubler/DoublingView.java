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
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;


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
        setBackground(new Color(60, 179, 113));
        setBorder(new CompoundBorder(new MatteBorder(7, 7, 7, 7, (Color) new Color(46, 139, 87)), new MatteBorder(3, 3, 3, 3, (Color) new Color(47, 79, 79))));
        setLayout(new MigLayout("", "[5%][20%][20%,grow][10%][20%][20%][5%]", "[15%][15%][25%][25%,grow][20%]"));
        setPreferredSize(new Dimension(510, 310));
        
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
        panel.setOpaque(false);
        JLabel lblBalanceMessages = new JLabel("Balance / Messages");
        panel.add(lblBalanceMessages);
        add(panel, "cell 0 0 7 1,grow");
        
        pnlHistory = new HistoryPanel();
        add(pnlHistory, "cell 1 1 5 1,grow");
        
        JPanel pnlRiskPayout = new JPanel();
        pnlRiskPayout.setOpaque(false);
        add(pnlRiskPayout, "cell 2 3 3 1,grow");
        pnlRiskPayout.setLayout(new BorderLayout(0, 0));
        
        lblRiskPayout = new JLabel("Risk / Payout");
        lblRiskPayout.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 30));
        lblRiskPayout.setHorizontalAlignment(SwingConstants.CENTER);
        pnlRiskPayout.add(lblRiskPayout);
        
        JPanel pnlOtherInfo = new JPanel();
        pnlOtherInfo.setOpaque(false);
        JLabel lblSomeOtherInfo = new JLabel("Some other info");
        pnlOtherInfo.add(lblSomeOtherInfo);
        add(pnlOtherInfo, "cell 0 4 7 1,grow");
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
