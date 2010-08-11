package com.jeff.fx.datastore;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TimeOfWeek;

@Component("storeValidator")
public class StoreValidator {

	@Autowired
	private ForexiteCandleWeekLoader loader;

	private FXDataSource dataSource = FXDataSource.Forexite;
	private Instrument instrument = Instrument.EURGBP;
	private Period period = Period.OneMin;
	private LocalDate startDate = new LocalDate(2010, 7, 25);
	private LocalDate endDate = new LocalDate();

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		StoreValidator sv = (StoreValidator) ctx.getBean("storeValidator");
		sv.run();
	}
	
	public void run() {
		validateWeek(startDate);
	}
	
	public void validateWeek(LocalDate date) {

		FXDataRequest request = new FXDataRequest();
		request.setDataSource(dataSource);
		request.setDate(date);
		request.setEndDate(date);
		request.setInstrument(instrument);
		request.setPeriod(period);
		
		try {
			
			CandleWeek cw = loader.load(request.getInstrument(), request.getDate(), request.getPeriod());
			
			// create a lookup table
			int candleSize = (int)(period.getInterval() / 1000 / 60);
			int candlesInDay = (int)(1440 / candleSize);
			boolean[] markers = new boolean[candlesInDay];
			
			// mark each valid candle
			for(int i=0; i<cw.getCandleCount(); i++) {
				
				CandleDataPoint candle = cw.getCandle(i);
				
				if(candle.getPeriod() != period) {
					System.out.println("invalid period: " + candle);
				} else if(candle.getDataSource() != dataSource) {
					System.out.println("invalid data source: " + candle);
				} else if(candle.getInstrument() != instrument) {
					System.out.println("invalid instrument: " + candle);
				} else if(candle.getDate().getMinuteOfHour() % candleSize != 0) {
					System.out.println("invalid time: " + candle);
				} else if(candle.getBuyOpen() == 0) {
					System.out.println("missing candle: " + (new TimeOfWeek(i)));
				} else {
					int minuteOfDay = (candle.getDate().getMillisOfDay() / 1000 / 60);
					int periodOfDay = minuteOfDay / candleSize;
					markers[periodOfDay] = true;
				}
			}

			// print the invalid candles
			System.out.println("Errors:");
			LocalDateTime dt = new LocalDateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0);
			for(int i=0; i<candlesInDay; i++) {
				LocalDateTime time = dt.plusMinutes(i * candleSize);
				if(!markers[i]) {
					System.out.println(time);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
