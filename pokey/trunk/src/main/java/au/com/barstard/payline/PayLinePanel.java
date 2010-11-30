package au.com.barstard.payline;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;

/**
 * Title:        Pokey Simulator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      MBlue Pty Ltd
 * @author
 * @version 1.0
 */

public class PayLinePanel extends JPanel
{
    private int pays[] = null;

    private BorderLayout borderLayout1 = new BorderLayout();

    public void setPays(int[] newPays)
    {
        pays = newPays;
        repaint();
    }

    private void paintLine(Graphics2D g, int index, int yoffset)
    {
        g.setColor(Color.black);
        g.fillRect(0, yoffset, 60, 20);

        g.setFont(new Font("Arial", Font.PLAIN, 8));
        g.setColor(Color.white);
        g.drawString("" + (index + 1), 3, yoffset + 5);
        g.drawString("" + pays[index], 40, yoffset + 5);

//        for (int j = 0; j < 5; j++)
//        {
//            for (int k = -1; k < 2; k++)
//            {
//                if (k == PayLineProcessor.payLineModel[index][j])
//                {
//                    if (pays[index] > 0)
//                    {
//                        g.setColor(new Color(0, 255, 0));
//                    }
//                    else
//                    {
//                        g.setColor(new Color(0, 0, 255));
//                    }
//                }
//                else
//                {
//                    if (pays[index] > 0)
//                    {
//                        g.setColor(new Color(0, 92, 0));
//                    }
//                    else
//                    {
//                        g.setColor(new Color(0, 0, 92));
//                    }
//                }
//                g.drawRect(j * 4 + 15, yoffset + k * 4, 3, 3);
//            }
//        }
    }

    public void paint(Graphics g)
    {
//        BufferedImage bi = new BufferedImage(60, LineProcessor.PayLineProcessor.length * 21, BufferedImage.TYPE_INT_RGB);
//
//        for (int i = 0; i < LineProcessor.PayLineProcessor.length; i++)
//        {
//            paintLine(bi.createGraphics(), i, i * 21 + 6);
//        }
//
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(bi, 0, 0, this);
    }

    public PayLinePanel()
    {
//        pays = new int[LineProcessor.PayLineProcessor.length];
//
//        for (int i = 0; i < pays.length; i++)
//        {
//            pays[i] = 0;
//        }
//
//        this.setLayout(borderLayout1);
    }
}