package com.jeff.fx.backtest.chart;

import org.jfree.data.xy.DefaultHighLowDataset;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTranslatingFormat extends DecimalFormat {
	
	private static final long serialVersionUID = -77241543489699720L;
	private DefaultHighLowDataset dataset = null;
    private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DateTranslatingFormat(DefaultHighLowDataset dataset)
    {
        this.dataset = dataset;
    }

    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)
    {
    	if (Double.isNaN(number))
            return toAppendTo;
        
        Date date = dataset.getXDate(0, (int)number);

        return toAppendTo.append(fmt.format(date));
    }
}
