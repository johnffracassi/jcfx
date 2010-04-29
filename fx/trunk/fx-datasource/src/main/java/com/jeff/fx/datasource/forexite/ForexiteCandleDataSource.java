package com.jeff.fx.datasource.forexite;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.datasource.ZippedAbstractDataSource;

@Component("ForexiteCandleDataSource")
public class ForexiteCandleDataSource extends ZippedAbstractDataSource<CandleDataPoint> {
	
	private static Logger log = Logger.getLogger(ForexiteCandleDataSource.class);

	@Override
	public FXDataResponse<CandleDataPoint> load(FXDataRequest request) throws Exception {
		
		log.debug(request);
		
		// return an empty list for a Saturday
		if(request.getInterval().getStart().getDayOfWeek() == DateTimeConstants.SATURDAY) {
			return new FXDataResponse<CandleDataPoint>(request, Collections.<CandleDataPoint>emptyList());
		} else {		
			return super.load(request);
		}
	}
}
