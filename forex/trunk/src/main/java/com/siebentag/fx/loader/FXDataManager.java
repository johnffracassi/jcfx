package com.siebentag.fx.loader;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.gain.GainTickReader;
import com.siebentag.util.Config;

@Component("app")
public class FXDataManager
{
	private FXDataLoader<TickDataPoint> output;
	
	public static void main(String[] args)
    {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    FXDataManager app = (FXDataManager)ctx.getBean("app");
	    app.run();
    }
	
	public void run()
	{
	    GainTickReader tickReader = new GainTickReader();
//	    tickReader.setListener(output);
	    
	    try
	    {
	    	output.prepare();
	    	
	    	File dir = new File(Config.getInputDir() + "/gain/2009-01/");
	    	
	    	for(File file : dir.listFiles())
	    	{
	    		tickReader.readFile(file);
	    	}

	    	output.complete();
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
    }
}
