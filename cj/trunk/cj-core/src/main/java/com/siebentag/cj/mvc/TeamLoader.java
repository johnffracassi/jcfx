package com.siebentag.cj.mvc;

import org.springframework.stereotype.Component;

import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.PlayerCollection;
import com.siebentag.cj.model.Team;

@Component
public class TeamLoader
{
	private static long counter = 1;
	
	public Team loadTeam(String teamName)
	{
		Team team = new Team();
		
		if("aus".equalsIgnoreCase(teamName))
		{
			team.setHjid(1L);
			team.setName("Australia");
		}
		else if("eng".equalsIgnoreCase(teamName))
		{
			team.setHjid(2L);
			team.setName("England");
		}

		PlayerCollection players = new PlayerCollection();
		
		for(int i=0; i<11; i++)
		{
			players.getPlayers().add(getPlayer(teamName.toUpperCase() + (i+1), teamName + "Player" + (i+1)));
		}
		
		team.setPlayers(players);
		
		return team;
	}
	
	public Player getPlayer(String firstName, String surname)
	{
		Player player = new Player();

		player.setHjid(counter++);
		player.setKey(surname + "-" + firstName.charAt(0));
		player.setFirstName(firstName);
		player.setSurname(surname);
		
		return player;
	}
}
