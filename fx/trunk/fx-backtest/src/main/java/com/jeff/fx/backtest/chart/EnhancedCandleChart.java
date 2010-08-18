package com.jeff.fx.backtest.chart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.OHLCDataset;

import com.jeff.fx.common.CandleCollection;

public class EnhancedCandleChart extends JXPanel {
	
	private static final long serialVersionUID = -3540048295595301949L;

	private NumberAxis domainAxis;
	private NumberAxis rangeAxis;
	private int firstCandle = 0;
	private int candlesPerView = 175;

	public EnhancedCandleChart(CandleCollection cc) {
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
		zoomScrollBar.setMinimum(2);
		zoomScrollBar.setValue(candlesPerView / 25);
		zoomScrollBar.setMaximum(200);
		zoomScrollBar.setPreferredSize(new Dimension(150, zoomScrollBar.getHeight()));
		zoomScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				setZoom(e.getValue() * 25);
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
	
    /**
     * Creates a sample chart.
     * @param dataset  a dataset.
     * @return a sample chart.
     */
    private JFreeChart createChart(CandleCollection candles) {

    	CandleCollectionDataset ccd = new CandleCollectionDataset("Candles", candles);
    	
        final JFreeChart chart = createCandlestickChart(ccd, true);
        
        // set domain/category/x-axis bounds
        domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
        domainAxis.setRange(0, 200);
        domainAxis.setAutoRangeMinimumSize(200);

        // set range/value/y-axis bounds
        rangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeMinimumSize(0.005);

        // add indicators
        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDataset(1, new TypicalValueDataset("Typical", candles));
        plot.setRenderer(1, new StandardXYItemRenderer());
        
        return chart;
    }

    private JFreeChart createCandlestickChart(OHLCDataset dataset, boolean legend) {
    	NumberAxis timeAxis = new NumberAxis();
		NumberAxis valueAxis = new NumberAxis();
		XYPlot plot = new XYPlot(dataset, timeAxis, valueAxis, null);
		plot.setRenderer(new CandlestickRenderer());
		JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
        timeAxis.setNumberFormatOverride(new DateTranslatingFormat((CandleCollectionDataset)chart.getXYPlot().getDataset()));
		return chart;
    } 
}
