package com.jeff.fx.backtest.chart;

import com.jeff.fx.common.CandleCollection;
import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class TypicalValueChart extends JXPanel {
	
	private static final long serialVersionUID = -3540048295595301949L;

	private JFreeChart chart;
	private NumberAxis domainAxis;
	private NumberAxis rangeAxis;
	private int firstCandle = 0;
	private int candlesPerView = 1000;
	private int multiplier = 100;
	private JScrollBar offsetScrollBar = new JScrollBar();
	private CandleCollection candles;
	private ChartPanel panel;
	private JMenu mnuIndicators;
	private JMenu mnuView;

	public TypicalValueChart() {
		
		setLayout(new BorderLayout());
		
		candles = new CandleCollection();
		panel = createPanel(candles);
		add(panel, BorderLayout.CENTER);
		
		JXPanel pnlScrolls = new JXPanel(new BorderLayout());
		offsetScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		offsetScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				scrollTo(e.getValue());
			}
		});
		
		JScrollBar zoomScrollBar = new JScrollBar();
		zoomScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		zoomScrollBar.setMinimum(10);
		zoomScrollBar.setValue(candlesPerView / multiplier);
		zoomScrollBar.setMaximum(200);
		zoomScrollBar.setPreferredSize(new Dimension(150, zoomScrollBar.getHeight()));
		zoomScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				setZoom(e.getValue() * multiplier);
			}
		});
		pnlScrolls.add(offsetScrollBar, BorderLayout.CENTER);
		pnlScrolls.add(zoomScrollBar, BorderLayout.EAST);
		add(pnlScrolls, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);
		
		mnuIndicators = new JMenu("");
		mnuIndicators.setIcon(new ImageIcon(TypicalValueChart.class.getResource("/images/chart_line.png")));
		menuBar.add(mnuIndicators);
		
		mnuView = new JMenu("");
		mnuView.setIcon(new ImageIcon(TypicalValueChart.class.getResource("/images/magnifier.png")));
		menuBar.add(mnuView);
		
		JMenuItem mntmResetZoom = new JMenuItem("Reset Zoom");
		mnuView.add(mntmResetZoom);
	}
	
	public void update(CandleCollection candles) {
		this.candles = candles;
		offsetScrollBar.setMaximum(candles.getCandleCount() - candlesPerView);
		panel.setChart(createChart(candles));
	}
	
	public void setZoom(int zoom) {
		candlesPerView = zoom;
		scrollTo(firstCandle);
	}
	
	public void scrollTo(int idx) {
		firstCandle = idx;
		int lastCandle = firstCandle + candlesPerView;
		domainAxis.setRange(firstCandle, lastCandle);
	}
	
	private ChartPanel createPanel(CandleCollection candles) {
		JFreeChart chart = createChart(candles);
		return new ChartPanel(chart);
	}
	
	public JFreeChart getChart() {
		return chart;
	}
	
    /**
     * Creates a sample chart.
     * @param dataset  a dataset.
     * @return a sample chart.
     */
    private JFreeChart createChart(CandleCollection candles) {

    	TypicalValueDataset ccd = new TypicalValueDataset("Price", candles);
    	
        chart = ChartFactory.createXYLineChart(null, null, null, ccd, PlotOrientation.VERTICAL, false, false, false);

        // set domain/category/x-axis bounds
        domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
//        domainAxis.setNumberFormatOverride(new DateTranslatingFormat(ccd));
//        domainAxis.setRange(0, 200);
//        domainAxis.setAutoRangeMinimumSize(200);

        // set range/value/y-axis bounds
        rangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeMinimumSize(0.005);

        return chart;
    }
    
	public JMenu getIndicatorsMenu() {
		return mnuIndicators;
	}
	
	public JMenu getViewMenu() {
		return mnuView;
	}
}
