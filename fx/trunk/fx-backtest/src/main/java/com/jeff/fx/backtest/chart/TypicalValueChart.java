package com.jeff.fx.backtest.chart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.indicator.Indicator;

public class TypicalValueChart extends JXPanel {
	
	private static final long serialVersionUID = -3540048295595301949L;

	private JFreeChart chart;
	private NumberAxis domainAxis;
	private NumberAxis rangeAxis;
	private int firstCandle = 0;
	private int candlesPerView = 1000;
	private int multiplier = 100;

	public TypicalValueChart(CandleCollection cc) {
		
		setLayout(new BorderLayout());
		
		ChartPanel pnl = createPanel(cc);
		add(pnl, BorderLayout.CENTER);
		
		JXPanel pnlScrolls = new JXPanel(new BorderLayout());
		JScrollBar offsetScrollBar = new JScrollBar();
		offsetScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		offsetScrollBar.setMaximum(cc.getCandleCount() - candlesPerView);
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
	
	public void addIndicator(Indicator indicator) {
		final XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDataset(plot.getDatasetCount(), new IndicatorDataset("Typical", indicator));
		plot.setRenderer(plot.getDatasetCount(), new StandardXYItemRenderer());
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
        domainAxis.setNumberFormatOverride(new DateTranslatingFormat((TypicalValueDataset)chart.getXYPlot().getDataset()));
//        domainAxis.setRange(0, 200);
//        domainAxis.setAutoRangeMinimumSize(200);

        // set range/value/y-axis bounds
        rangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeMinimumSize(0.005);

        return chart;
    }
}
