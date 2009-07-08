package com.siebentag.sw;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.siebentag.cj.model.Competition;

public class DataLoader
{
	private BasicDAO dao; 
	
	public static void main(String[] args)
    {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContextCreate.xml");
	    DataLoader dl = (DataLoader)ctx.getBean("dataLoader");
	    dl.run();
    }

	public void run()
	{
		DataConverter dc = new DataConverter();
//		Competition mc = dc.convertCompetition("X:/dev/proj/SteamboatWillies.com/data/game");
		Competition mc = dc.convertCompetition("/media/Jeff/dev/proj/SteamboatWillies.com/data/game");
//		Competition mc = dc.convertCompetition("/media/physical/sata1500a/dev/proj/SteamboatWillies.com/data/game");
		
		dao.save(mc);
	}
	
	public BasicDAO getDao()
    {
    	return dao;
    }

	public void setDao(BasicDAO dao)
    {
    	this.dao = dao;
    }
}
