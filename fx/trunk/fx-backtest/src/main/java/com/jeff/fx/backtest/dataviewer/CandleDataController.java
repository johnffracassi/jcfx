package com.jeff.fx.backtest.dataviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartPanel;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.chart.SimplePriceChart;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.orderbook.OrderSelectionListener;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleWeek;

@Component
public class CandleDataController implements OrderSelectionListener {

	private CandleCollection data;
	private int currentWeek = 0;
	private CandleDataView view;
    private CandleDataTableModel model;

    public CandleDataController() {
    	
    	view = new CandleDataView();
    	model = new CandleDataTableModel();
        
    	// setup the table
    	view.getTable().setModel(model);
    	view.getTable().setColumnControlVisible(true);
    	for(int i=0; i<model.getColumnCount(); i++) {
    		if(model.getRenderer(i) != null) {
    			view.getTable().getColumn(i).setCellRenderer(model.getRenderer(i));
    		}
    	}

    	// setup the listeners
    	view.getBtnNextWeek().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ev) { nextWeek(); }
    	});
    	view.getBtnPrevWeek().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ev) { prevWeek(); }
    	});
    }

    public void setCandles(CandleCollection candles) {
    	this.data = candles;
    	this.currentWeek = 0;
    	update();
    }
    
    public CandleDataView getView() {
    	return view;
    }
    
    private void nextWeek() {
    	currentWeek = (currentWeek + 1) % data.getWeekCount();
    	update();
    }
    
    private void prevWeek() {
    	currentWeek = (currentWeek - 1) % data.getWeekCount();
    	update();
    }
    
    private void update() {
    	model.update(data.getCandleWeek(currentWeek));
    }

	public void orderSelected(BTOrder order) {
		
		// update the data table
		model.update(data.getCandleWeek(order.getOpenTime().toLocalDate()), order.getOpenTime(), order.getCloseTime());
		
		// update the summary data
		CandleWeek cw = data.getCandleWeek(currentWeek);
		view.getLblDetails().setText(String.format("%s / %s / %s", cw.getDataSource(), cw.getInstrument(), cw.getPeriod()));
		view.getLblOpenTime().setText(String.valueOf(order.getOpenTime()));
		view.getLblCloseTime().setText(String.valueOf(order.getCloseTime()));
		view.getLblCandleCount().setText(String.valueOf(model.getRowCount()));
		view.getLblPriceRange().setText("-");
		view.getLblOpenClose().setText(String.format("%.4f / %.4f", 0.0, 0.0));
		
		// update the chart
		ChartPanel chart = SimplePriceChart.createChart(String.valueOf(cw.getInstrument()), data, order.getOpenTime(), order.getCloseTime());
		view.setPnlChart(chart);
	}
}
