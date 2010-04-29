package com.jeff.fx.datamanager;

import javax.swing.table.DefaultTableModel;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataManager;

public class DataTableModel extends DefaultTableModel  {
	
	private DataManager dataManager;
	
	public DataTableModel() {
		dataManager = (DataManager)DataManagerApp.ctx.getBean("dataManager");
	}
	
	public int getColumnCount() {
		return 2 + Period.values().length;
	}

	public String getColumnName(int arg0) {
		if(arg0 == 0) {
			return "Date";
		} else if(arg0 == Period.values().length + 1) {
			return "Load";
		} else {
			return Period.values()[arg0 - 1].key;
		}
	}

	public int getRowCount() {
		LocalDate startOfTime = new LocalDate(2010, 3, 1);
		LocalDate now = new LocalDate();
		
		Days days = Days.daysBetween(startOfTime, now);
		int dayCount = days.getDays();
		
		return dayCount;
	}

	public LocalDate getDateForRow(int row) {
		LocalDate now = new LocalDate();
		return now.minusDays(row);
	}
	
	public Object getValueAt(int row, int col) {
		
		LocalDate now = getDateForRow(row);
		
		if(col == 0) {
			return now.toString("yyyy-MM-dd");
		} else if(col == 13) {
			return "Load";
		} else {
			FXDataRequest req = new FXDataRequest();
			req.setDataSource(FXDataSource.Forexite);
			req.setInstrument(Instrument.AUDUSD);
			req.setDate(now);
			req.setPeriod(Period.values()[col-1]);
			
			return (dataManager.exists(req) ? "Y":"N");
		}
	}
}