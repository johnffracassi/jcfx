package com.siebentag.fx.source.forexcom;

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.siebentag.fx.TickDataPoint;

public class RateServicePanel extends JPanel implements TickListener
{
	public RateServicePanel()
	{
		RateService rateService = new RateService();
		rateService.addListener(this);
	}
	
	public void tick(TickDataPoint tick)
	{
		System.out.println(tick);
	}
}

class RateTableModel extends DefaultTableModel
{
	private Map<String,TickDataPoint> data;
}