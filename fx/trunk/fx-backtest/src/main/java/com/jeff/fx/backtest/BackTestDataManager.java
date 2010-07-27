package com.jeff.fx.backtest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataStoreImpl;
import com.jeff.fx.datastore.DataStoreProgress;

@Component
public class BackTestDataManager {
	
	@Autowired
	private DataStoreImpl dataManager;
	
	public List<CandleDataPoint> getCandles() throws Exception {
		
		// build the data request from the user parameters
		FXDataSource dataSource = FXDataSource.valueOf(AppCtx.retrieve("newChart.dataSource"));
		Instrument instrument = Instrument.valueOf(AppCtx.retrieve("newChart.instrument"));
		Period period = Period.valueOf(AppCtx.retrieve("newChart.period"));
		FXDataRequest request = new FXDataRequest(dataSource, instrument, AppCtx.retrieveDate("newChart.startDate"), AppCtx.retrieveDate("newChart.endDate"), period);
		
		// run the data load in a new thread
		DataLoaderWorker dlw = new DataLoaderWorker(request);
		dlw.execute();
		FXDataResponse<CandleDataPoint> response = dlw.get();
		
		return response.getData();
	}

	/**
	 * Load dataset and monitor progress
	 */
	class DataLoaderWorker extends SwingWorker<FXDataResponse<CandleDataPoint>, Integer> {
		
		private FXDataRequest request;
		private int dayCount; 
			
		public DataLoaderWorker(FXDataRequest request) {
			
			this.request = request;
			this.dayCount = Days.daysBetween(request.getDate(), request.getEndDate()).getDays();
			
			// give user feedback using the popup progress monitor
			addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if ("day".equals(evt.getPropertyName())) {
						final int day = (Integer)evt.getNewValue();						
						ProgressMonitor.update(new DataStoreProgress(day, dayCount));
					}
				}
			});
		}
		
		/**
		 * load the data day by day
		 */
		protected FXDataResponse<CandleDataPoint> doInBackground() throws Exception {
			List<CandleDataPoint> all = new LinkedList<CandleDataPoint>();
			for(int day = 0; day<dayCount; day++) {
				FXDataRequest newReq = new FXDataRequest(request);
				newReq.setDate(request.getDate().plusDays(day));
				newReq.setEndDate(null);
				all.addAll(dataManager.loadCandlesForDay(newReq).getData());
				firePropertyChange("day", day, day+1);
			}
	
			return new FXDataResponse<CandleDataPoint>(request, all);
		}
	}
}