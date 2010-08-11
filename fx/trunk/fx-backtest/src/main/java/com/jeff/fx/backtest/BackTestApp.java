package com.jeff.fx.backtest;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.chart.CandleChart;
import com.jeff.fx.backtest.chart.NewCandleChartEvent;
import com.jeff.fx.backtest.strategy.time.NewTimeStrategyChartEvent;
import com.jeff.fx.backtest.strategy.time.StrategyView;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

@Component("backTestApp")
public class BackTestApp {
	
	private static Logger log = Logger.getLogger(BackTestApp.class);

	private BackTestFrame frame;
	
	@Autowired
	private BackTestDataManager dataManager;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		BackTestApp app = (BackTestApp)ctx.getBean("backTestApp");
		AppCtx.init(ctx);
		app.run();
	}

	private void run() {
		
		log.info("Starting application");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("Building application frame");
		frame = new BackTestFrame();
		frame.init();
		frame.setVisible(true);
		
		log.info("Registering event listeners");
		AppCtx.register(NewCandleChartEvent.class, new FXActionEventListener() {
			public void event(com.jeff.fx.backtest.FXActionEvent ev) {
				try {
					Instrument instrument = Instrument.valueOf(AppCtx.retrieve("newChart.instrument"));
					Period period = Period.valueOf(AppCtx.retrieve("newChart.period"));
					CandleCollection candles = dataManager.getCandles();
//					ChartPanel chart = CandleChart.createChart(instrument + " (" + period.key + ")", candles);
//					frame.addMainPanel(chart, instrument + " (" + period.key + ")");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		AppCtx.register(NewTimeStrategyChartEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				StrategyView tsv = new StrategyView();
				frame.addMainPanel(tsv, "Time Strategy");
				tsv.initialise();
			}
		});
		
		log.info("Application initialised");
	}
}

