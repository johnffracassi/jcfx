package com.jeff.fx.backtest.chart;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.JScrollBar;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.indicator.IndicatorMarker;
import com.jeff.fx.indicator.ZigZagIndicator;

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
    	
    	NumberAxis timeAxis = new NumberAxis();
		NumberAxis valueAxis = new NumberAxis();
		XYPlot plot = new XYPlot(ccd, timeAxis, valueAxis, null);
		
		plot.setRenderer(new CandlestickRenderer());
		JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        timeAxis.setNumberFormatOverride(new DateTranslatingFormat((CandleCollectionDataset)chart.getXYPlot().getDataset()));
        
        // set domain/category/x-axis bounds
        domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
        domainAxis.setRange(0, 200);
        domainAxis.setAutoRangeMinimumSize(200);

        // set range/value/y-axis bounds
        rangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeMinimumSize(0.005);

        // add indicators
        ZigZagIndicator zzi = new ZigZagIndicator();
        List<IndicatorMarker> markers = zzi.calculate(candles);
        XYSeries zzis = new XYSeries("Zig-Zag");
        for(IndicatorMarker marker : markers) {
        	
        	// add point to the line
        	zzis.add((double)marker.getIndex(), marker.getValue());
        	
        	// add marker and label
            final CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f), null);
            final XYAnnotation bestBid = new XYDrawableAnnotation(marker.getIndex(), marker.getValue(), 11, 11, cd);
            plot.addAnnotation(bestBid);
            final XYPointerAnnotation pointer = new XYPointerAnnotation(marker.getLabel(), marker.getIndex(), marker.getValue(), marker.getLabelLocation() * 3.0 * Math.PI / 4.0);
            pointer.setBaseRadius(35.0);
            pointer.setTipRadius(10.0);
            pointer.setFont(new Font("SansSerif", Font.PLAIN, 9));
            pointer.setPaint(Color.blue);
            pointer.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
            plot.addAnnotation(pointer);        
        }

        plot.setDataset(1, new XYSeriesCollection(zzis));
        plot.setRenderer(1, new StandardXYItemRenderer());
        plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
        
        return chart;
    }
}
