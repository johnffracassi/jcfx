package com.jeff.fx.backtest.chart;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import com.jeff.fx.backtest.GenericDialog;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.gui.beanform.BeanForm;
import com.jeff.fx.indicator.ChartType;
import com.jeff.fx.indicator.ChartTypes;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.overlay.SimpleMovingAverage;
import com.jeff.fx.indicator.overlay.ZigZagIndicator;

public class PriceChartController
{

    private TypicalValueChart view;
    private CandleCollection candles;
    private IndicatorCache indicators = new IndicatorCache();

    public PriceChartController()
    {

        view = new TypicalValueChart();
        view.getIndicatorsMenu().add(new NewSimpleMovingAverageAction("Simple Moving Average"));
        view.getIndicatorsMenu().add(new NewZigZagAction("Zig Zag"));
    }

    public TypicalValueChart getView()
    {
        return view;
    }

    public void update(CandleCollection candles)
    {
        this.candles = candles;
        view.update(candles);
    }

    public void addIndicator(Indicator indicator)
    {

        if (indicator.getClass().isAnnotationPresent(ChartType.class))
        {
            ChartType annotation = indicator.getClass().getAnnotation(ChartType.class);
            ChartTypes type = annotation.value();

            if (type == ChartTypes.PriceRelative)
            {

                XYPlot plot = (XYPlot) view.getChart().getPlot();
                plot.setDataset(plot.getDatasetCount(), new IndicatorDataset(indicator.getDisplayName(), indicator));
                plot.setRenderer(plot.getDatasetCount(), new StandardXYItemRenderer());

            }
            else if (type == ChartTypes.Oscillator)
            {

            }
            else if (type == ChartTypes.Annotated)
            {

            }
        }
    }

    class NewSimpleMovingAverageAction extends AbstractAction
    {
        public NewSimpleMovingAverageAction(String string)
        {
            super("Simple Moving Average");
        }

        public void actionPerformed(ActionEvent ev)
        {
            SimpleMovingAverage sma = new SimpleMovingAverage(14, CandleValueModel.Typical);
            BeanForm beanForm = new BeanForm();
            beanForm.buildForm(sma);
            GenericDialog gd = new GenericDialog(beanForm, "Simple Moving Average");
            gd.setVisible(true);

            if (gd.isDone())
            {
                addIndicator(indicators.calculate(sma, candles));
            }
        }
    }

    class NewZigZagAction extends AbstractAction
    {
        public NewZigZagAction(String string)
        {
            super(string);
        }

        public void actionPerformed(ActionEvent ev)
        {
            addIndicator(indicators.calculate(new ZigZagIndicator(30), candles));
        }
    }
}