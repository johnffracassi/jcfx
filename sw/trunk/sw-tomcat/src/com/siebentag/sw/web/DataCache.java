package com.siebentag.sw.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.siebentag.cj.model.Match;
import com.siebentag.cj.model.Player;
import com.siebentag.sw.BasicDAO;

public class DataCache
{
	private BasicDAO dao;
	private Map<String,Player> players;
	private Map<Long,Match> matches;
	private Date lastMatchDate;

	public static void main(String[] args)
    {
	    DataCache dc = new DataCache();
	    
	    Player p = dc.getPlayer("jeff");
	    System.out.println(p.getSurname());
    }
	
	private BasicDAO getDao()
	{
		if(dao == null)
		{
			init();
		}
		
		return dao;
	}
	
	private void init()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContextUpdate.xml");
		dao = (BasicDAO)ctx.getBean("dao");
	}
	
	public Player getPlayer(String id)
	{
		if(players == null)
		{
			refreshPlayerCache();
		}
		
		if(players.containsKey(id))
		{
			return players.get(id);
		}
		else
		{
			Player p = new Player();
			p.setUsername(id);
			p.setFirstName(id);
			p.setSurname(id);
			return p;
		}
	}
	
	public Match getMatch(long matchId)
	{
		if(matches == null)
		{
			refreshMatchCache();
		}
		
		return matches.get(matchId);
	}
	
	private List<Match> loadMatches()
	{
		List<Match> matches = getDao().getHibernate().loadAll(Match.class);
		return matches;
	}
	
	private List<Player> loadPlayers()
	{
		List<Player> players = getDao().getHibernate().loadAll(Player.class);
		return players;
	}
	
	private void refreshPlayerCache()
	{
		List<Player> playerList = loadPlayers();
		
		players = new HashMap<String, Player>();
		
		for(Player player : playerList)
		{
			players.put(player.getUsername(), player);
		}
	}
	
	private void refreshMatchCache()
	{
		List<Match> matchList = loadMatches();
		
		lastMatchDate = null;
		matches = new HashMap<Long,Match>();
		
		for(Match match : matchList)
		{
			matches.put(match.getObjectId(), match);
			
			Date matchDate = match.getStartDate().toGregorianCalendar().getTime(); 
			if(lastMatchDate == null || matchDate.after(lastMatchDate))
			{
				lastMatchDate = matchDate;
			}
		}

		// adjust time to 1am
		lastMatchDate.setHours(1);
		lastMatchDate.setMinutes(0);
	}

	public Date getLastMatchDate()
    {
    	return lastMatchDate;
    }

	public void setLastMatchDate(Date lastMatchDate)
    {
    	this.lastMatchDate = lastMatchDate;
    }
}
