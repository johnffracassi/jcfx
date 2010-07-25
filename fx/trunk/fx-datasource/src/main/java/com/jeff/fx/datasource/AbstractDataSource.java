package com.jeff.fx.datasource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.util.Downloader;

public class AbstractDataSource<T extends FXDataPoint> implements DataSource<T> {

	private static Logger log = Logger.getLogger(AbstractDataSource.class);

	private Downloader downloader;
	private Locator locator;
	private Parser<T> parser;

	public FXDataResponse<T> load(FXDataRequest request) throws Exception {
		
		List<T> dataPoints = new ArrayList<T>();

		LocalDate date = request.getDate();
		if(date.getDayOfWeek() != DateTimeConstants.SATURDAY) {
			String url = locate(request.getInstrument(), date, request.getPeriod());
			
			try {
				byte[] compressed = download(url);
				
				if(compressed.length > 0) {
					byte[] uncompressed = process(compressed);
					List<T> newPoints = parse(uncompressed);
					dataPoints.addAll(newPoints);
				}
			} catch(FileNotFoundException fnfex) {
				log.warn("File not found", fnfex);
			}
		}

		return new FXDataResponse<T>(request, dataPoints);
	}
	
	public String locate(Instrument instrument, LocalDate date, Period period) {
		return locator.generateUrl(instrument, date, period);
	}

	public byte[] download(String url) throws IOException {
		return downloader.download(url);
	}

	public byte[] process(byte[] data) throws IOException {
		return data;
	}

	public List<T> parse(byte[] data) {
		return parser.readFile(new String(data));
	}
	
	public Locator getLocator() {
		return locator;
	}

	public void setLocator(Locator locator) {
		this.locator = locator;
	}

	public Parser<T> getParser() {
		return parser;
	}

	public void setParser(Parser<T> parser) {
		this.parser = parser;
	}

	public Downloader getDownloader() {
		return downloader;
	}

	public void setDownloader(Downloader downloader) {
		this.downloader = downloader;
	}
}
