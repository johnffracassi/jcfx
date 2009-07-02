package com.siebentag.fx.mv;

import java.awt.event.ActionEvent;
import java.sql.Time;
import java.util.Date;

import org.jdesktop.swingx.action.AbstractActionExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class RefreshDataAction extends AbstractActionExt
{
	private static final long serialVersionUID = -7284448188342647379L;

	@Autowired
	private DataCriteria dataSelector;
	
	@Autowired 
	private DataDisplay dataDisplay;
	
	@Autowired
	private DeltaTableDAO deltaTableDao;
	
	@Autowired 
	private DataSummaryPanel dataSummary;
	
	public RefreshDataAction()
	{
		super("Load");
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		Date startDate = dataSelector.getDateRange().getStart();
		Date endDate = dataSelector.getDateRange().getEnd();

		long windowSize = dataSelector.getWindowSize().getInterval();
		
		Time startTime = dataSelector.getTimeRange().getStart();
		Time endTime = dataSelector.getTimeRange().getEnd();

		try
		{
			TickAggregationDeltaList deltaList = deltaTableDao.getDeltaTable(startDate, endDate, FXDataSource.GAIN, dataSelector.getInstrument(), startTime, endTime);
			dataDisplay.setDeltaList(deltaList);
			dataSummary.setDeltaList(deltaList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
