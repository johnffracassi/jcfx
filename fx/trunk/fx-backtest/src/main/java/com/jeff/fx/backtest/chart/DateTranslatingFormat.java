package com.jeff.fx.backtest.chart;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTranslatingFormat extends DecimalFormat {
	
	private static final long serialVersionUID = -7724154348969179720L;
	final CandleCollectionDataset dataset;
    final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DateTranslatingFormat(CandleCollectionDataset ds) {
        dataset = ds;
    }
    
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        
    	if (Double.isNaN(number))
            return toAppendTo;
        
        double timeval = dataset.getDisplayXValue(0, (int)number);
        if (Double.isNaN(timeval))
            return toAppendTo;
        
        return toAppendTo.append(fmt.format(new Date((long)timeval)));
    }
}
