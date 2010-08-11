package com.jeff.fx.backtest.dataviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.gui.PriceCellRenderer;

@Component
public class CandleDataController {

	private CandleCollection data;
	private int currentWeek = 0;
	private CandleDataView view;
    private CandleDataTableModel model;

    public CandleDataController() {
    	
    	view = new CandleDataView();
    	model = new CandleDataTableModel();
        
    	view.getTable().setModel(model);
    	view.getTable().setColumnControlVisible(true);
    	view.getTable().getColumn(4).setCellRenderer(new PriceCellRenderer(4));
    	view.getTable().getColumn(5).setCellRenderer(new PriceCellRenderer(4));
    	view.getTable().getColumn(6).setCellRenderer(new PriceCellRenderer(4));
    	view.getTable().getColumn(7).setCellRenderer(new PriceCellRenderer(4));
    	view.getTable().getColumn(8).setCellRenderer(new PriceCellRenderer(0));

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
}
