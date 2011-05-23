package com.jeff.fx.backtest.chart;

import com.jeff.fx.backtest.chart.candle.MiniDataLoader;
import com.jeff.fx.common.*;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.gui.GUIUtil;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Component
public class MiniLoaderTest {

	@Autowired
	private MiniDataLoader loader;

	public static void main(String[] args)
    {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		MiniLoaderTest app = (MiniLoaderTest)ctx.getBean("miniLoaderTest");
		app.run();
	}

	public void run()
    {
        try
        {
            List<CandleDataPoint> candles = loader.load(FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin, new LocalDateTime(2010, 2, 23, 13, 00, 00), 10);
            for (CandleDataPoint candle : candles)
            {
                System.out.println(candle);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
