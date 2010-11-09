package com.jeff.fx.backtest.chart;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataResponse;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.gui.GUIUtil;
import com.jeff.fx.indicator.overlay.SimpleMovingAverage;

@Component
public class CandleChartTest {

	@Autowired
	private CandleDataStore dataManager;
		
	private TypicalValueChart chart;
	private CandleCollection candles;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		CandleChartTest app = (CandleChartTest)ctx.getBean("candleChartTest");
		app.run();
	}

	public void run() {
		
		JFrame frame = new JFrame("Candle Chart Test");
		frame.setLayout(new BorderLayout());
		frame.setSize(1000, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			CandleDataResponse cdr = dataManager.loadCandles(new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 8, 1), new LocalDate(2010, 8, 10), Period.OneMin));
			candles = cdr.getCandles();
//			chart = new EnhancedCandleChart(cc);
			
			//@hack broke so this all compiles
//			chart = new TypicalValueChart(candles);
			frame.add(GUIUtil.frame("Chart date", chart), BorderLayout.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JButton btnAdd = new JButton("Add SMA");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int periods = new Integer(JOptionPane.showInputDialog("SMA periods"));
				//@hack broke so this all compiles
//				chart.addIndicator(IndicatorCache.calculate(new SimpleMovingAverage(periods, CandleValueModel.Typical), candles));
			}
		});
		frame.add(btnAdd, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
}
