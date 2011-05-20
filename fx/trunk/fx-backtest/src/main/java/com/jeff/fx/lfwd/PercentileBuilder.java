package com.jeff.fx.lfwd;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PercentileBuilder
{
    private static CandleValueModel cvm = CandleValueModel.Close;

    public static double[][] buildTable(List<List<CandleDataPoint>> collections, int bands)
    {
        int size = findLargestListSize(collections);

        double[][] table = new double[bands + 1][size];

        for(int i=0; i<size; i++)
        {
            List<Double> prices = getPrices(i, collections);

            for(int p=0; p<=bands; p++)
            {
                table[p][i] = findPercentile(prices, p*(100/bands));
            }
        }

        return table;
    }

    private static double findPercentile(List<Double> nums, int percentile)
    {
        Collections.sort(nums);
        int idx = (int)((nums.size()-1) * (0.01 * percentile));
        return nums.get(idx);
    }

    private static int findLargestListSize(List<List<CandleDataPoint>> collections)
    {
        int largest = 0;
        for (List<CandleDataPoint> collection : collections)
        {
            if(collection.size() > largest)
            {
                largest = collection.size();
            }
        }
        return largest;
    }

    private static List<Double> getPrices(int idx, List<List<CandleDataPoint>> collections)
    {
        List<Double> prices = new ArrayList<Double>(collections.size());

        for (List<CandleDataPoint> candles : collections)
        {
            if(candles.size() > idx)
            {
                double price = getPrice(idx, candles);
                prices.add(price);
            }
        }

        return prices;
    }

    private static double getPrice(int idx, List<CandleDataPoint> candles)
    {
        if(idx == 0)
            return 0.0;
        else if(cvm.evaluate(candles.get(idx)) > 0)
            return cvm.evaluate(candles.get(idx)) - cvm.evaluate(candles.get(0));
        else
            return Double.NaN;
    }
}
