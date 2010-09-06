package com.jeff.fx.datastore;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

@Component("storeValidator")
public class StoreValidator {

	@Autowired
	private CandleDataStore loader;

	private FXDataSource dataSource = FXDataSource.Forexite;
	private Instrument instrument = Instrument.AUDUSD;
	private Period period = Period.FifteenMin;
	private LocalDate startDate = new LocalDate(2010, 7, 25);

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		StoreValidator sv = (StoreValidator) ctx.getBean("storeValidator");
		sv.run();
	}
	
	public void run() {

		FXDataRequest request = new FXDataRequest();
		request.setDataSource(dataSource);
		request.setDate(startDate);
		request.setEndDate(startDate);
		request.setInstrument(instrument);
		request.setPeriod(period);
		
		try {
			
			CandleWeek cw1 = loader.loadCandlesForWeek(new FXDataRequest(FXDataSource.Forexite, request.getInstrument(), request.getDate(), Period.OneMin));
			CandleWeek cw = new CandleWeek(cw1, Period.FifteenMin);
			
			LocalDateTime startTime = cw.getCandle(0).getDateTime();
			int candleSize = (int)(period.getInterval() / 60000);
			for(int i=0, n=cw.getCandleCount(); i<n; i++) {
				LocalDateTime time = startTime.plusMinutes(i * candleSize);
				System.out.printf("%d\t%s\t%s\t%.4f\t%.4f\t%.4f\t%.4f%n", i, time.toLocalDate(), time.toLocalTime(), cw.getRawValues(0)[i], cw.getRawValues(1)[i], cw.getRawValues(2)[i], cw.getRawValues(3)[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
