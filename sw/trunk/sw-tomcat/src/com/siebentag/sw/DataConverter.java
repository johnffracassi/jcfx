package com.siebentag.sw;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import com.siebentag.cj.model.Ball;
import com.siebentag.cj.model.BallCollection;
import com.siebentag.cj.model.BatsmanInnings;
import com.siebentag.cj.model.BatsmanInningsCollection;
import com.siebentag.cj.model.BoundaryType;
import com.siebentag.cj.model.BowlerInnings;
import com.siebentag.cj.model.BowlerInningsCollection;
import com.siebentag.cj.model.Competition;
import com.siebentag.cj.model.Extra;
import com.siebentag.cj.model.Match;
import com.siebentag.cj.model.MatchCollection;
import com.siebentag.cj.model.MatchInnings;
import com.siebentag.cj.model.Over;
import com.siebentag.cj.model.OverCollection;
import com.siebentag.cj.model.Partnership;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Season;
import com.siebentag.cj.model.SeasonCollection;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.model.Wicket;
import com.siebentag.cj.model.WicketType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class DataConverter
{
//	static String dataRoot      = "X:/dev/proj/SteamboatWillies.com/data";
	static String dataRoot 		= "/media/Jeff/dev/proj/SteamboatWillies.com/data";
	static String gameDir 		= dataRoot + "/game";
	static String profileDir 	= dataRoot + "/profile";

	private SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
	private Calendar cal = GregorianCalendar.getInstance();

	private Map<String,Player> players = new HashMap<String, Player>();
	private Map<String,Team> teams = new HashMap<String, Team>();
	private Map<String,Season> seasons;
	
	public static void main(String[] args)
    {
	    DataConverter dc = new DataConverter();
	    Player player = dc.convertPlayer("jeff");
	    System.out.println(player.getDisplayName());
    }

	public Competition convertCompetition(String gameDataDir)
	{
		Competition comp = new Competition();
		comp.setDescription("Indoor Cricket");
		comp.setCompetitionId("Indoor");
		comp.setName("Indoor");
		comp.setSeasons(new SeasonCollection());
		
		doMatchConversions(gameDataDir);

		SeasonCollection sc = new SeasonCollection();
		sc.setSeasons(new ArrayList<Season>(seasons.values()));
		comp.setSeasons(sc);
		
		return comp;
	}
	
	public Season getSeason(String seasonId)
	{
		if(seasons == null)
		{
			setupSeasonMap();
		}
		
		return seasons.get(seasonId);
	}
	
	private void setupSeasonMap()
	{
		seasons = new HashMap<String, Season>();
		seasons.put("1", createSeason("1", "Q3 2006 (A)"));
		seasons.put("2", createSeason("2", "Q3 2006 (B)"));
		seasons.put("3", createSeason("3", "Q1 2007 (B)"));
		seasons.put("4", createSeason("4", "Q2 2007 (A)"));
		seasons.put("5", createSeason("5", "Q2 2007 (B)"));
		seasons.put("6", createSeason("6", "Q1 2008 (B)"));
		seasons.put("7", createSeason("7", "Q4 2008 (B-Mo)"));
		seasons.put("8", createSeason("8", "Q4 2008 (B-Th)"));
		seasons.put("9", createSeason("9", "Q1 2009 (B)"));
	}
	
	public Season createSeason(String id, String description)
	{
		Season season = new Season();
		season.setSeasonId(id);
		season.setDescription(description);
		season.setMatches(new MatchCollection());
		return season;
	}
	
	private void doMatchConversions(String gameDataDir)
	{
		File dir = new File(gameDataDir);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.toLowerCase().endsWith(".game");
			} 
		});

		Arrays.sort(files, new Comparator<File>() {
			public int compare(File o1, File o2) {
	            return o1.getName().compareTo(o2.getName());
            }
		});
		
//		File file = files[73];
		for(File file : files)
		{
			System.out.println("Converting: " + file.getPath());
			Match match = convertMatch(file);
			
			String seasonId = file.getName().substring(0,1);
			Season season = getSeason(seasonId);
			season.getMatches().getMatches().add(match);
		}
	}
	
	private Player convertPlayer(String playerId)
	{
		Player player = new Player();
		player.setUsername(playerId);
		
		File file = new File(profileDir + "/" + playerId.toUpperCase() + ".profile");
		
		if(!file.exists())
		{
			player.setDisplayName(playerId);
			player.setFirstName(playerId);
			player.setSurname(playerId);
		}
		
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
		    String strLine;
	
			while ((strLine = br.readLine()) != null)   
			{
				String key = getKey(strLine);
				String value = getValue(strLine);
				
				if("fullName".equals(key))
				{
					player.setDisplayName(value);
					
					String[] names = value.split(" ");
					if(value.length() == 1)
					{
						player.setFirstName(value);
					}
					else if(value.length() == 2)
					{
						player.setFirstName(names[0]);
						player.setSurname(names[1]);
					}
					else
					{
						player.setFirstName(names[0]);
						player.setSurname(names[names.length-1]);
					}
				}
				else if("flag".equals(key))
				{
					player.setOrigin(value.substring(0, value.indexOf('.')));
				}
			}
			
			br.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return player;
	}
	
	private Match convertMatch(File file)
	{
		Match match = new Match();
		BatsmanInningsCollection batic = new BatsmanInningsCollection();
		BowlerInningsCollection bwlic = new BowlerInningsCollection();
		MatchInnings mi = new MatchInnings();
		mi.setBatting(batic);
		mi.setBowling(bwlic);
		match.getInnings().add(mi);
		
		int batInnsOrd = 1;
		int bowlInnsOrd = 1;
		
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
		    String strLine;
		    int lineNum = 1;

			while ((strLine = br.readLine()) != null)   
			{
				if(lineNum == 1)
				{
					// season
				}
				else if(lineNum == 2)
				{
					try { 
						match.setRoundNumber(Integer.parseInt(getValue(strLine)));
					} catch(Exception ex) {}
					
					match.setDescription(getValue(strLine));
				}
				else if(lineNum == 3)
				{
					match.setStartDate(stringToXmlDate(getValue(strLine)));
				}
				else if(lineNum == 4)
				{
					try
					{
						String[] timeSplit = getValue(strLine).split(":");
						match.getStartDate().setHour(Integer.parseInt(timeSplit[0]));
						match.getStartDate().setMinute(Integer.parseInt(timeSplit[1]));
					}
					catch(Exception ex)
					{
						match.getStartDate().setHour(12);
						match.getStartDate().setMinute(0);
					}
				}
				else if(lineNum == 5)
				{
					Team team = getTeam(getValue(strLine));
					match.setHomeTeam(team);
				}
				else if(lineNum == 6)
				{
					Team team = getTeam(getValue(strLine));
					match.setAwayTeam(team);
				}
				else if(lineNum >= 8 && lineNum <= 15)
				{
					if(strLine.trim().length() > 0)
					{
						BatsmanInnings inns = convertBatsmanInnings(strLine);
						inns.setOrdinal(batInnsOrd++);
						mi.getBatting().getInnings().add(inns);
					}
				}
				else if(lineNum >= 16 && lineNum <= 31)
				{
					if(strLine.trim().length() > 0)
					{
						BowlerInnings inns = convertBowlerInnings(strLine);
						inns.setOrdinal(bowlInnsOrd);
						inns.getOvers().getOvers().get(0).setOrdinal(bowlInnsOrd);
						mi.getBowling().getInnings().add(inns);
						bowlInnsOrd++;
					}
				}
				
				lineNum ++;
			}

			//Close the input stream
			in.close();
			
			setPartnerships(batic);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return match;
	}
	
	private void setPartnerships(BatsmanInningsCollection bic)
	{
		for(int i=0; i<bic.getInnings().size(); i+=2)
		{
			BatsmanInnings bi1 = bic.getInnings().get(i);
			BatsmanInnings bi2 = bic.getInnings().get(i+1);

			setPartners(bi1, bi2);
			
			Partnership ps = new Partnership();
			ps.setOrdinal(i / 2 + 1);
			ps.getBalls().addAll(bi1.getBalls().getBalls());
			ps.getBalls().addAll(bi2.getBalls().getBalls());
			bic.getPartnerships().add(ps);
		}
	}
	
	private void setPartners(BatsmanInnings i1, BatsmanInnings i2)
	{
		for(Ball ball : i1.getBalls().getBalls())
		{
			ball.setStriker(i1.getPlayer());
			ball.setNonStriker(i2.getPlayer());
		}

		for(Ball ball : i2.getBalls().getBalls())
		{
			ball.setStriker(i2.getPlayer());
			ball.setNonStriker(i1.getPlayer());
		}
	}
		
	private Team getTeam(String str)
	{
		if(teams.containsKey(str))
		{
			return teams.get(str);
		}
		else
		{
			Team team = new Team();
			team.setId(str);
			team.setName(str);
			teams.put(str, team);
			return team;
		}
	}
	
	private Player getPlayer(String str)
	{
		str = str.toLowerCase();
		
		if(players.containsKey(str))
		{
			return players.get(str);
		}
		else
		{
			Player player = convertPlayer(str);
			players.put(str, player);
			return player;
		}
	}
	
	private BowlerInnings convertBowlerInnings(String line)
	{
		BowlerInnings bi = new BowlerInnings();

		String[] ballArray = line.split("\t");
		
		Player player = getPlayer(ballArray[0]);
		bi.setPlayer(player);
		
		Over over = convertOver(ballArray);
		
		OverCollection oc = new OverCollection();
		oc.getOvers().add(over);
		
		bi.setOvers(oc);
		
		for(Ball ball : over.getBalls().getBalls())
		{
			ball.setBowler(player);
		}
		
		return bi;
	}
	
	private Over convertOver(String[] ballArray)
	{
		Over over = new Over();
		
		over.setBalls(convertBalls(ballArray));
		
		return over;
	}
	
	private BatsmanInnings convertBatsmanInnings(String line)
	{
		BatsmanInnings bi = new BatsmanInnings();

		String[] ballArray = line.split("\t");
		
		Player player = getPlayer(ballArray[0]);
		bi.setPlayer(player);
		
		BallCollection bc = convertBalls(ballArray);
		bi.setBalls(bc);
		
		return bi;
	}
	
	private BallCollection convertBalls(String[] ballArray)
	{
		BallCollection balls = new BallCollection();
		
		for(int i=1; i<ballArray.length; i++)
		{
			Ball ball = stringToBall(ballArray[i].toUpperCase());
			
			if(ball != null)
			{
				ball.setOrdinal(i);
				balls.getBalls().add(ball);
			}
		}
		
		return balls;
	}

	private Ball stringToBall(final String ballDesc)
	{
		if(ballDesc == null || ballDesc.trim().length() == 0)
		{
			return null;
		}
		
		Ball ball = new Ball();
		
		String ballString = ballDesc.toUpperCase();
		ball.setExtra(stringToExtra(ballString));
		ball.setWicket(stringToWicket(ballString));
		
		int runs = 0;
		if(ball.getWicket() != null && ball.getWicket().getWicketType() != WicketType.NOT_OUT)
		{
			runs = -5;
		}
		else
		{
			ballString = ballString.substring(ballString.length() - 1, ballString.length());
			
			try
			{
				runs = Integer.parseInt(ballString);
				
				if(runs >= 7)
				{
					ball.setBoundaryType(BoundaryType.SIX);
				}
				else if(runs >= 5)
				{
					ball.setBoundaryType(BoundaryType.FOUR);
				}
			}
			catch(NumberFormatException ex)
			{
			}
			
			if(ball.getExtra() != null)
			{
				runs += (ball.getExtra().getNoball() + ball.getExtra().getWide());
			}
		}
		
		ball.setRunsForBatsman(runs);
		ball.setRunsForBowler(runs);
		ball.setRunsForTeam(runs);
		ball.setBallCountsForBatsman(true);
		ball.setBallCountsForBowler(true);
		
		return ball;
	}
	
	private Wicket stringToWicket(String ballString)
	{
		Wicket wicket = new Wicket();
		
		if(ballString.startsWith("B"))
		{
			wicket.setWicketType(WicketType.BOWLED);
		}
		else if(ballString.startsWith("C"))
		{
			wicket.setWicketType(WicketType.CAUGHT);
		}
		else if(ballString.startsWith("R"))
		{
			wicket.setWicketType(WicketType.RUN_OUT);
		}
		else if(ballString.startsWith("S"))
		{
			wicket.setWicketType(WicketType.STUMPED);
		}
		else if(ballString.startsWith("L"))
		{
			wicket.setWicketType(WicketType.LBW);
		}
		else if(ballString.startsWith("M"))
		{
			wicket.setWicketType(WicketType.MANKAD);
		}
		else
		{
			return null;
		}
		
		return wicket;
	}
	
	private Extra stringToExtra(String ballString)
	{
		Extra ex = new Extra();
		
		if(ballString.toUpperCase().startsWith("NB"))
		{
			ex.setNoball(2);
		}
		else if(ballString.toUpperCase().startsWith("W"))
		{
			ex.setWide(2);
		}
		else
		{
			return null;
		}
		
		return ex;
	}
	
	private XMLGregorianCalendar stringToXmlDate(String str)
		throws ParseException, Exception
	{
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.setTime(sdf.parse(str));
		return new XMLGregorianCalendarImpl((GregorianCalendar)cal);
	}
	
	private String getValue(String str) throws Exception
	{
		try
		{
			String[] split = str.split("=");
			return split[1].trim();
		}
		catch(Exception ex)
		{
			System.err.print("Error attempting to split: [" + str + "]");
			throw ex;
		}
	}
	
	private String getKey(String str) throws Exception
	{
		try
		{
			String[] split = str.split("=");
			return split[0].trim();
		}
		catch(Exception ex)
		{
			System.err.print("Error attempting to split: [" + str + "]");
			throw ex;
		}
	}
}
