package com.jeff.fx.lfwd.candletype;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CandleTypeTestForm extends JFrame
{
    private JSlider sldOpen = new JSlider(JSlider.VERTICAL);
    private JSlider sldClose = new JSlider(JSlider.VERTICAL);
    private JSlider sldSize = new JSlider(JSlider.VERTICAL);
    private JTextArea txt = new JTextArea();

    public static void main(String[] args)
    {
        CandleTypeTestForm form = new CandleTypeTestForm();
        form.setVisible(true);
    }

    public CandleTypeTestForm()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(250, 400));
        setLayout(new BorderLayout());

        JPanel pnlSliders = new JPanel(new FlowLayout());
        pnlSliders.setLayout(new FlowLayout());
        pnlSliders.add(sldSize);
        pnlSliders.add(sldOpen);
        pnlSliders.add(sldClose);

        sldOpen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                changed();
            }
        });
        sldClose.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                changed();
            }
        });
        sldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                changed();
            }
        });

        add(pnlSliders, BorderLayout.CENTER);

        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setRows(4);

        add(txt, BorderLayout.SOUTH);
    }

    private void changed()
    {

        double price = 1.0500;
        double spread = 0.0004;

        CandleDataPoint candle = new CandleDataPoint();
        candle.setInstrument(Instrument.AUDUSD);
        candle.setPeriod(Period.FifteenMin);

        double low   = price;
        double high  = price + sldSize.getValue()/10000.0;
        double open  = price + sldSize.getValue()*(sldOpen.getValue()/100.0)/10000.0;
        double close = price + sldSize.getValue()*(sldClose.getValue()/100.0)/10000.0;

        candle.setBuyOpen(open - spread);
        candle.setSellOpen(open + spread);
        candle.setBuyClose(close - spread);
        candle.setSellClose(close + spread);
        candle.setBuyHigh(high - spread);
        candle.setSellHigh(high + spread);
        candle.setBuyLow(low - spread);
        candle.setSellLow(low + spread);

        StringBuffer buf = new StringBuffer(String.format("%.4f / %.4f / %.4f / %.4f %n", candle.getOpen(), candle.getHigh(), candle.getLow(), candle.getClose()));

        for (CandleType candleType : CandleType.values())
        {
            if(candleType.is(candle))
            {
                buf.append(candleType.toString()).append(", ");
            }
        }

        if(buf.length() > 2)
            txt.setText(buf.substring(0, buf.length()-2));
        else
            txt.setText("None");
    }
}
