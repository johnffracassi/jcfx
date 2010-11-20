package au.com.barstard.controlpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ControlPanelView extends JPanel
{
    private int[] creditModel = new int[] { 1, 3, 5, 10, 20 };
    private int[] lineModel = new int[] { 1, 2, 5, 10, 25 };

    public ControlPanelView(final ControlPanelController controller)
    {
        assert (creditModel.length == lineModel.length);

        setPreferredSize(new Dimension(600, 200));
        setBackground(new Color(46, 139, 87));
        
        String layoutStr = "";
        for (int i = 0; i < creditModel.length + 2; i++)
        {
            layoutStr += String.format("[%.0f%%,fill]", 100.0 / (creditModel.length + 2));
        }

        setLayout(new MigLayout("", layoutStr, "[50%,fill][50%,fill]"));

        JButton btnReset = new JButton("Reset");
        add(btnReset, "cell 0 0");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                controller.reset();
            }
        });

        JButton btnGamble = new JButton("Gamble");
        add(btnGamble, "cell " + (creditModel.length + 1) + " 0");
        btnGamble.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                controller.gamble();
            }
        });

        JButton btnHelp = new JButton("Help");
        add(btnHelp, "cell 0 1");
        btnHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                controller.help();
            }
        });

        JButton btnTakeWin = new JButton("Take");
        add(btnTakeWin, "cell " + (creditModel.length + 1) + " 1");
        btnTakeWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                controller.takeWin();
            }
        });

        for (int i = 0; i < creditModel.length; i++)
        {
            final int idx = i;

            JButton btnCredits = new JButton(creditModel[i] + " cr" + (creditModel[i] > 1 ? "s" : ""));
            add(btnCredits, "cell " + (i + 1) + " 0");
            btnCredits.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    controller.setCreditsPerLine(creditModel[idx]);
                }
            });

            JButton btnLines = new JButton(lineModel[i] + " ln" + (lineModel[i] > 1 ? "s" : ""));
            add(btnLines, "cell " + (i + 1) + " 1");
            btnLines.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    controller.setLinesPlaying(lineModel[idx]);
                }
            });
        }
    }
}
