package com.jeff.fx.datastore;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.CachedDownloader;
import com.jeff.fx.datasource.forexite.ForexiteCandleReader;
import com.jeff.fx.datasource.forexite.ForexiteLocator;
import com.jeff.fx.util.DateUtil;
import com.jeff.fx.util.ZipUtil;

@Component("forexiteCandleWeekLoader")
public class ForexiteCandleWeekLoader {

	@Autowired
	private CachedDownloader downloader;
	
	@Autowired
	private ForexiteLocator locator;
	
	@Autowired
	private ForexiteCandleReader reader;
	
	/**
	 * Load candles from the actual data source (forexite website)
	 * 
	 * @param instrument
	 * @param date
	 * @param period
	 * @return
	 * @throws IOException
	 */
	public CandleWeek load(Instrument instrument, LocalDate date, Period period) throws IOException {
		
		LocalDate startDate = DateUtil.getStartOfWeek(date);
		CandleWeek cw = new CandleWeek(startDate, FXDataSource.Forexite, instrument, period);
		
		for(int i=0; i<6; i++) {
			String url = locator.generateUrl(instrument, startDate.plusDays(i), period);
			byte[] downloadedData = downloader.download(url);
			if(downloadedData.length > 0) {
				String data = new String(ZipUtil.unzipBytes(downloadedData));
				String[] lines = data.split("\n");
				for(String line : lines) {
					if(line.startsWith(instrument.toString())) {
						CandleDataPoint candle = reader.line(line, 0);
						cw.setCandle(candle);
					}
				}
			} 
		}
		
		cw.fillGaps();	 
		
		return cw;
	}
}
