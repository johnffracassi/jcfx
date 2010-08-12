package com.jeff.fx.backtest;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.chart.NewCandleChartEvent;
import com.jeff.fx.backtest.strategy.time.NewTimeStrategyChartEvent;
import com.jeff.fx.backtest.strategy.time.StrategyView;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

@Component("backTestApp")
public class BackTestApp {
	
	private static Logger log = Logger.getLogger(BackTestApp.class);

	@Autowired
	private BackTestFrame frame;
	
	@Autowired
	private BackTestDataManager dataManager;
	
	public static void main(String[] args) {
		log.info("Setting up Spring context");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");

		log.info("Getting application bean from Spring context");
		BackTestApp app = (BackTestApp)ctx.getBean("backTestApp");
		
		AppCtx.initialise(ctx);
		
		app.run();
	}

	private void run() {
		
		log.info("Starting application");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Slider.paintValue", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("Building application frame");
		frame.init();
		frame.setVisible(true);
		
		log.info("Registering event listeners");
		AppCtx.registerEventListener(NewCandleChartEvent.class, new FXActionEventListener() {
			public void event(com.jeff.fx.backtest.FXActionEvent ev) {
				try {
					Instrument instrument = Instrument.valueOf(AppCtx.getPersistent("newChart.instrument"));
					Period period = Period.valueOf(AppCtx.getPersistent("newChart.period"));
					CandleCollection candles = dataManager.getCandles();
//					ChartPanel chart = CandleChart.createChart(instrument + " (" + period.key + ")", candles);
//					frame.addMainPanel(chart, instrument + " (" + period.key + ")");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		AppCtx.registerEventListener(NewTimeStrategyChartEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				StrategyView tsv = new StrategyView();
				frame.addMainPanel(tsv, "Time Strategy");
				tsv.initialise();
			}
		});
		
		log.info("Application initialised");
	}
}

