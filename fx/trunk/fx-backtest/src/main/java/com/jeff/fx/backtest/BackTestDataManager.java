package com.jeff.fx.backtest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataResponse;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.datastore.DataStoreProgress;
import com.jeff.fx.gui.ProgressMonitor;

@Component
public class BackTestDataManager {
	
	private static Logger log = Logger.getLogger(BackTestDataManager.class);

	@Autowired
	private CandleDataStore dataManager;
	
	public CandleCollection getCandles() throws Exception {
		
		// build the data request from the user parameters
		FXDataSource dataSource = FXDataSource.valueOf(AppCtx.getPersistent("newChart.dataSource"));
		Instrument instrument = Instrument.valueOf(AppCtx.getPersistent("newChart.instrument"));
		Period period = Period.valueOf(AppCtx.getPersistent("newChart.period"));
		FXDataRequest request = new FXDataRequest(dataSource, instrument, AppCtx.getPersistentDate("newChart.startDate"), AppCtx.getPersistentDate("newChart.endDate"), period);
		
		// run the data load in a new thread
		DataLoaderWorker dlw = new DataLoaderWorker(request);
		
		dlw.execute();
		CandleDataResponse response = dlw.get();
//		CandleDataResponse response = dlw.doInBackground();
		
		return response.getCandles();
	}

	public void clearStoreCache() throws IOException{
		dataManager.clearStoreCache();
	}
	
	/**
	 * Load dataset and monitor progress
	 */
	class DataLoaderWorker extends SwingWorker<CandleDataResponse, Integer> {
		
		private FXDataRequest request;
		private int dayCount; 
			
		public DataLoaderWorker(FXDataRequest request) {
			
			this.request = request;
			this.dayCount = Days.daysBetween(request.getDate(), request.getEndDate()).getDays();
			
			// give user feedback using the popup progress monitor
			addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if ("day".equals(evt.getPropertyName())) {
						int day = (Integer)evt.getNewValue();
						ProgressMonitor.update(new DataStoreProgress(day, dayCount));
					} else if("complete".equals(evt.getPropertyName())) {
						ProgressMonitor.complete();
					}
				}
			});
		}

		protected void done() {
			ProgressMonitor.complete();
		}
		
		/**
		 * load the data day by day
		 */
		public CandleDataResponse doInBackground() throws Exception {
			
			CandleCollection data = new CandleCollection();
			for(int day = 0; day<dayCount; day+=7) {
				FXDataRequest newReq = new FXDataRequest(request);
				newReq.setDate(request.getDate().plusDays(day));
				newReq.setEndDate(null);

				log.debug("Loading candle data (" + day + "/" + dayCount + " = " + newReq.getDate() + ")");

				CandleWeek cw = dataManager.loadCandlesForWeek(newReq);
				data.putCandleWeek(cw);
				firePropertyChange("day", day, day+1);
			}
	
			firePropertyChange("complete", false, true);
			return new CandleDataResponse(request, data);
		}
	}
}