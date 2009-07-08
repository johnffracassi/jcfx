package com.siebentag.sw.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.siebentag.cj.model.Match;
import com.siebentag.cj.model.Player;

public class SWFunctions
{
	private static DataCache cache = new DataCache();
	private static SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yy H:mm");
	
	public static String name(String name)
	{
		Player player = cache.getPlayer(name);
		
		String fullname = player.getFirstName() + " " + player.getSurname();
		String flagIcon = "<img src=\"images/" + player.getOrigin() + ".gif\">";
		String profileLink = "<a href=\"profile-" + name + ".html\">" + fullname + "</a>";
		
		return flagIcon + " " + profileLink;
	}
	
	public static String match(long matchId)
	{
		Match match = cache.getMatch(matchId);
		
		return "<a href=\"results.htm?matchId=" + matchId + "\">" +
				"<img border=\"0\" src=\"images/scorecard.png\" />" +
				"</a>&nbsp;" +
				match.getAwayTeam().getName() + " (" + sdf.format(match.getStartDate().toGregorianCalendar().getTime()) + ")";
	} 
	
	public static String isNewDate(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -7);
		
		boolean isNew = (date != null && date.after(now.getTime()));

		return isNew ? getNewIcon() : "";
	}
	
	public static String isNewMatch(long matchId)
	{
		Match match = cache.getMatch(matchId);
		long newMatchThreshold = cache.getLastMatchDate().getTime();
		long matchDate = match.getStartDateItem().getTime();
		
		return (newMatchThreshold <= matchDate) ? getNewIcon() : "";
	}
	
	private static String getNewIcon()
	{
		return "<img src=\"images/new.gif\" />";
	}
}
