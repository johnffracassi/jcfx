package com.jeff.fx.backtest.chart;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import com.jeff.fx.backtest.strategy.time.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.SimpleMovingAverage;
import com.jeff.fx.indicator.ZigZagIndicator;

public class PriceChartController {

	private TypicalValueChart view;
	private CandleCollection candles;
	
	public PriceChartController() {
		
		view = new TypicalValueChart();
		view.getIndicatorsMenu().add(new NewSimpleMovingAverageAction("Simple Moving Average"));
		view.getIndicatorsMenu().add(new NewZigZagAction("Zig Zag"));
	}
	
	public TypicalValueChart getView() {
		return view;
	}
	
	public void update(CandleCollection candles) {
		this.candles = candles;
		view.update(candles);
	}
	
	public void addIndicator(Indicator indicator) {
		
		if(indicator.getClass().isAnnotationPresent(ChartType.class)) {
			ChartType annotation = indicator.getClass().getAnnotation(ChartType.class);
			ChartTypes type = annotation.value();
			
			if(type == ChartTypes.PriceRelative) {
				
				XYPlot plot = (XYPlot) view.getChart().getPlot();
				plot.setDataset(plot.getDatasetCount(), new IndicatorDataset(indicator.getName(), indicator));
				plot.setRenderer(plot.getDatasetCount(), new StandardXYItemRenderer());
				
			} else if(type == ChartTypes.Oscillator) {
				
			} else if(type == ChartTypes.Annotated) {
				
			}
		}
	}

	class NewSimpleMovingAverageAction extends AbstractAction {
		public NewSimpleMovingAverageAction(String string) { super("Simple Moving Average"); }
		public void actionPerformed(ActionEvent ev) {
			addIndicator(IndicatorCache.calculate(new SimpleMovingAverage(15, CandleValueModel.Typical), candles));
		}
	}

	class NewZigZagAction extends AbstractAction {
		public NewZigZagAction(String string) { super(string); }
		public void actionPerformed(ActionEvent ev) {
			addIndicator(IndicatorCache.calculate(new ZigZagIndicator(30), candles));
		}
	}
}