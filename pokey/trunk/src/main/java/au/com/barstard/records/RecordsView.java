package au.com.barstard.records;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Font;

public class RecordsView extends JPanel
{
    private JLabel lblRecordName = new JLabel("Record Name");
    private JLabel[] lblPosition;
    private JLabel[] lblPlayerName;
    private JLabel[] lblValue;
    private JLabel[] lblDate;
    
    protected RecordsView(int rows)
    {
        setBackground(new Color(0, 128, 0));
        setBorder(new MatteBorder(15, 15, 15, 15, (Color) new Color(46, 139, 87)));
        lblPosition = new JLabel[rows];
        lblPlayerName = new JLabel[rows];
        lblValue = new JLabel[rows];
        lblDate = new JLabel[rows];
        
        setLayout(new MigLayout("", "[10%][30%][20%][35%]", "[][]"));
        lblRecordName.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 20));
        
        lblRecordName.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblRecordName, "cell 0 0 4 1,growx");

        for(int i=0; i<rows; i++)
        {
            lblPosition[i] = new JLabel((1+i) + ".");
            lblPosition[i].setHorizontalAlignment(JLabel.RIGHT); 
            lblPlayerName[i] = new JLabel("Player Name");
            lblValue[i] = new JLabel("Value");
            lblDate[i] = new JLabel("Date");
            add(lblPosition[i], "cell 0 " + (i+1));
            add(lblPlayerName[i], "cell 1 " + (i+1));
            add(lblValue[i], "cell 2 " + (i+1));
            add(lblDate[i], "cell 3 " + (i+1));
        }
    }

    protected void setRecord(int idx, int pos, String name, String value, String date)
    {
        lblPosition[idx].setText(pos + ".");
        lblPlayerName[idx].setText(name);
        lblValue[idx].setText(value);
        lblDate[idx].setText(date);
    }
    
    protected JLabel getLblRecordName()
    {
        return lblRecordName;
    }
}
