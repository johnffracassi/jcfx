package com.jeff.fx.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.util.Downloader;

public class AbstractDataSource<T extends FXDataPoint> implements DataSource<T> {

	@Autowired
	private Locator locator;

	@Autowired
	private Parser<T> parser;

	@Autowired
	@Qualifier("downloader")
	private Downloader downloader;

	public FXDataResponse<T> load(FXDataRequest request) throws Exception {
		List<T> dataPoints = new ArrayList<T>();

		String url = locate(request.getInstrument(), request.getDate(), request.getPeriod());
		byte[] compressed = download(url);
		byte[] uncompressed = process(compressed);
		List<T> newPoints = parse(uncompressed);
		dataPoints.addAll(newPoints);

		return new FXDataResponse<T>(request, newPoints);
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
}
