package com.jeff.fx.backtest;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataManager;

@Component("backTestApp")
public class BackTestApp {
	
	private static Logger log = Logger.getLogger(BackTester.class);

	private BackTestFrame frame;
	
	@Autowired
	private DataManager dataManager;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		BackTestApp app = (BackTestApp)ctx.getBean("backTestApp");
		app.run();
	}

	private void run() {
		log.info("Starting application");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		frame = new BackTestFrame();
		frame.init();
		frame.setVisible(true);
		
		AppCtx.register(NewCandleChartEvent.class, new FXActionEventListener() {
			public void event(com.jeff.fx.backtest.FXActionEvent ev) {
				try {
					FXDataSource dataSource = FXDataSource.valueOf(AppCtx.getString("newChart.dataSource"));
					Instrument instrument = Instrument.valueOf(AppCtx.getString("newChart.instrument"));
					Period period = Period.valueOf(AppCtx.getString("newChart.period"));
					FXDataRequest request = new FXDataRequest(dataSource, instrument, AppCtx.getDate("newChart.startDate"), AppCtx.getDate("newChart.endDate"), period);
					FXDataResponse<CandleDataPoint> candles = dataManager.loadCandles(request);
					ChartPanel chart = CandleChart.createChart(instrument + " (" + period.key + ")", candles.getData());
					frame.addMainPanel(chart, instrument + " (" + period.key + ")");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}

