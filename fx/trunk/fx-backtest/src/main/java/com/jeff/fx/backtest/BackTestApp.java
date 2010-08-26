package com.jeff.fx.backtest;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.chart.EnhancedCandleChart;
import com.jeff.fx.backtest.chart.NewCandleChartEvent;
import com.jeff.fx.backtest.chart.NewPriceChartEvent;
import com.jeff.fx.backtest.chart.PriceChartController;
import com.jeff.fx.backtest.strategy.coder.NewStrategyCoderEvent;
import com.jeff.fx.backtest.strategy.coder.StrategyCoderController;
import com.jeff.fx.backtest.strategy.time.NewTimeStrategyEvent;
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

		log.debug("Setting application look and feel");
		try {
			log.debug("Setting application look and feel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Slider.paintValue", false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Building application frame");
		frame.init();

		log.info("Registering event listeners");
		AppCtx.registerEventListener(NewCandleChartEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				try {
					Instrument instrument = Instrument.valueOf(AppCtx.getPersistent("newChart.instrument"));
					Period period = Period.valueOf(AppCtx.getPersistent("newChart.period"));
					CandleCollection candles = dataManager.getCandles();
					EnhancedCandleChart chart = new EnhancedCandleChart(candles);
					frame.addMainPanel(chart, instrument + " (" + period.key + ")");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});		
		
		AppCtx.registerEventListener(NewPriceChartEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				try {
					Instrument instrument = Instrument.valueOf(AppCtx.getPersistent("newChart.instrument"));
					Period period = Period.valueOf(AppCtx.getPersistent("newChart.period"));
					PriceChartController pcc = new PriceChartController();
					pcc.update(dataManager.getCandles());
					frame.addMainPanel(pcc.getView(), instrument + " (" + period.key + ")");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		AppCtx.registerEventListener(NewTimeStrategyEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				StrategyView tsv = new StrategyView();
				frame.addMainPanel(tsv, "Time Strategy");
				tsv.initialise();
			}
		});

		AppCtx.registerEventListener(NewStrategyCoderEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				StrategyCoderController controller = new StrategyCoderController();
				frame.addMainPanel(controller.getView(), "Strategy Coder");
			}
		});

		frame.setVisible(true);
		log.info("Application initialised");
	}
}

