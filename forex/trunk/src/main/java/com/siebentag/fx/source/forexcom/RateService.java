package com.siebentag.fx.source.forexcom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gain.rateservice.Bup;
import com.gain.rateservice.Msg;
import com.gain.rateservice.Rate;
import com.gain.rateservice.RateServiceListener;
import com.gain.rateservice.Sys;
import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.FXDataSource;

public class RateService implements RateServiceListener
{
	private com.gain.rateservice.RateService _rateService = null;
	private List<TickListener> listeners = new ArrayList<TickListener>();

	public void addListener(TickListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(TickListener listener)
	{
		listeners.remove(listener);
	}

	public static void main(String[] args)
	{
		try
		{
			// Print opening banner
			System.out.println("RateServiceExample using RateService version: " + com.gain.rateservice.RateService.getVersion());

			// Create a rates service with host, port key
			RateService example = new RateService();

			// start the example service
			example.start();

			// Sleep for the execute seconds stated by the parameter
			try
			{
				Thread.sleep(5000);
			}
			catch (Exception e)
			{}

			// stop the example service
			example.stop();

			// Sleep for the 5 seconds to allow the service to disconnect
			// cleanly
			try
			{
				Thread.sleep(5000);
			}
			catch (Exception e)
			{}

			// Print closing banenr
			System.out.println("RateServiceExample terminating ");
		}
		catch (Exception e)
		{
			System.out.println("RateServiceExample, Exception: " + e.getClass().getName() + " : " + e.getMessage());
		}
	}

	public RateService()
	{
		_rateService = new com.gain.rateservice.RateService(this, "demorates.efxnow.com", "443", "C4ACD286DC2E4AE5EC2CC436C390D611");
	}

	public void start()
	{
		_rateService.connect();
	}

	public void stop()
	{
		_rateService.disconnect();
	}

	/**
	 * Restarts the rates service
	 */
	public void restart()
	{
		if (_rateService.isConnected())
		{
			_rateService.disconnect();
		}

		try
		{
			Thread.sleep(5000);
		}
		catch (Exception e)
		{
		}

		_rateService.connect();
	}

	public void OnRateServiceConnected()
	{
	}

	public void OnRateServiceConnectionFailed(Exception e)
	{
		System.out.println("RateServiceExample: connection failed: " + e.getClass().getName() + " : " + e.getMessage());

		if (_rateService.getSuccessfullCounnectionCount() > 0 && _rateService.getFailedConsecutiveCounnectionCount() < 10)
		{
			restart();
		}
	}

	public void OnRateServiceConnectionLost(Exception e)
	{
		System.out.println("RateServiceExample: connection lost: " + e.getClass().getName() + " : " + e.getMessage());

		if (_rateService.getSuccessfullCounnectionCount() > 0 && _rateService.getFailedConsecutiveCounnectionCount() < 10)
		{
			restart();
		}
	}

	public void OnRateServiceDisconnected()
	{
		System.out.println("RateServiceExample: disconnected successfully");
	}

	public void OnRateServiceRate(Rate rate)
	{
		TickDataPoint tick = new TickDataPoint();
		
		tick.setDataSource(FXDataSource.GAIN);
		tick.setBuy(rate.getAsk());
		tick.setSell(rate.getBid());
		tick.setInstrument(rate.getCurrencyPair().replace("/", ""));
		tick.setDate(new Date());
		
		for(TickListener listener : listeners)
		{
			listener.tick(tick);
		}
	}

	public void OnRateServiceBUPMessage(Bup arg0)
	{
		System.out.println(arg0);
	}

	public void OnRateServiceMSGMessage(Msg arg0)
	{
		System.out.println(arg0);
	}

	public void OnRateServiceSYSMessage(Sys arg0)
	{
		System.out.println(arg0);
	}
}