package com.jeff.sw;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;


public class DataConverter extends Task
{
	static String dataRoot 		= "C:/temp/steamboat/data";
	static String gameDir 		= dataRoot + "/game";
	static String profileDir 	= dataRoot + "/profile";
	static String outputFile 	= dataRoot + "/xml/data.xml";
	
	int batBallId = 1;
	int bowlBallId = 1;
	int inningsId = 1;
	int partnershipId = 1;
	int overId = 1;
	int batOverId = 1;
	int matchId = 1;
	int currentSeason = 12;
	int activeSeason = -1;
	
	Set<String> players = new HashSet<String>();
	Set<String> currentPlayers = new HashSet<String>();
	
	public DataConverter()
	{
	}
	
	public void execute()
	{
		Document doc = generate();
		XMLOutputter.output(doc, outputFile);
	}
	
	public void setGameDir(String value) 
	{
		gameDir = value;
		log("set gameDir to " + value);
	}
	
	public void setProfileDir(String value)
	{
		profileDir = value;
		log("set profileDir to " + value);
	}
	
	public void setOutputFile(String value)
	{
		outputFile = value;
		log("set outputFile to " + value);
	}
	
	private Document generate() 
	{
    	Document doc = new DocumentImpl();
    	
    	Node nodeData = doc.createElement("data");
    	
    	Node nodeSeasons = doc.createElement("seasons");
    	nodeSeasons.appendChild(createSeason(doc, "1", "Q3", "2006", "A", "Jeffshire 2nd VIII"));
    	nodeSeasons.appendChild(createSeason(doc, "2", "Q4", "2006", "B", "Jeffshire 3rd VIII"));
    	nodeSeasons.appendChild(createSeason(doc, "3", "Q1", "2007", "B", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "4", "Q2", "2007", "A", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "5", "Q2", "2007", "B", "Angry Helen Kellers"));
    	nodeSeasons.appendChild(createSeason(doc, "6", "Q1", "2008", "B", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "7", "Q4", "2008", "B-M", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "8", "Q4", "2008", "B-Th", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "9", "Q1", "2009", "B", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "10", "Q2", "2009", "Th", "Steamboat Willies"));
    	nodeSeasons.appendChild(createSeason(doc, "11", "Q4", "2009", "Th", "Steamboat Willies")); 
    	nodeSeasons.appendChild(createSeason(doc, "12", "Q1", "2010", "A", "Steamboat Willies")); 
    	nodeSeasons.appendChild(createSeason(doc, "13", "Q2", "2010", "Superleague", "Steamboat Willies")); 
    	
    	Node nodeMatches = convertMatches(doc);
    	Node nodePlayers = convertProfiles(doc);
    	
    	nodeData.appendChild(nodeSeasons);
    	nodeData.appendChild(nodePlayers);
    	nodeData.appendChild(nodeMatches);
    	
    	doc.appendChild(nodeData);
    	
    	return doc;
	}
	
	private Node createSeason(Document doc, String seasonId, String name, String year, String grade, String team)
	{
		Element elem = doc.createElement("season");
		
		elem.setAttribute("id", seasonId);
		elem.setAttribute("name", name);
		elem.setAttribute("year", year);
		elem.setAttribute("grade", grade);
		elem.setAttribute("teamname", team);
		
		return elem;
	}
	
	private Node convertMatches(Document doc)
	{
		Node nodeMatches = doc.createElement("matches");
		
		File dir = new File(gameDir);
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
		
		for(File file : files)
		{
			Element elem = convertMatch(doc, file);
			elem.setAttribute("id", String.valueOf(matchId++));
			nodeMatches.appendChild(elem);
		}
		
		return nodeMatches;
	}
	
	private Node convertProfiles(Document doc)
	{
		Node node = doc.createElement("players");
		
		for(String player : players)
		{
			File file = new File(profileDir + "/" + player.toUpperCase() + ".profile");
			
			Element elem = doc.createElement("player");
			elem.setAttribute("id", player);
			elem.setAttribute("current", currentPlayers.contains(player) ? "Y" : "N");
			
			if(file.exists())
			{
				convertProfile(doc, elem, file);
			}
			
			node.appendChild(elem);
		}
		
		return node;
	}
	
	private void convertProfile(Document doc, Element elem, File file)
	{
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
		    String strLine;
			while ((strLine = br.readLine()) != null)   
			{
				String[] split = strLine.split("=");
				
				if(split[0].trim().equalsIgnoreCase("nicknames"))
				{
					if(split[1] != null && split[1].trim().length() > 0)
					{
						elem.appendChild(convertNicknames(doc, split[1]));
					}
				}
				if(split[0].trim().equalsIgnoreCase("quotes"))
				{
					if(split[1] != null && split[1].trim().length() > 0)
					{
						elem.appendChild(convertQuotes(doc, split[1]));
					}
				}
				else
				{
					Element attr = doc.createElement(split[0].trim());
					attr.appendChild(doc.createTextNode(split[1].trim()));
					elem.appendChild(attr);
				}
			}
		    
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private Element convertNicknames(Document doc, String str)
	{
		Element elem = doc.createElement("nicknames");
		
		String[] split = str.split(",");
		for(String nickname : split)
		{
			Element nn = doc.createElement("nickname");
			nn.setTextContent(nickname.trim());
			elem.appendChild(nn);
		}
		
		return elem;
	}
	
	private Element convertQuotes(Document doc, String str)
	{
		Element elem = doc.createElement("quotes");
		
		String[] split = str.split("\t");
		for(String quote : split)
		{
			Element nn = doc.createElement("quote");
			nn.setTextContent(quote.trim());
			elem.appendChild(nn);
		}
		
		return elem;
	}
	
	int batCum = 0;
	int bwlCum = 0;
	int batAbsOrd = 0;
	int bwlAbsOrd = 0;
	
	private Element convertMatch(Document doc, File file)
	{
		Element elem = doc.createElement("match");

		batCum = 0;
		bwlCum = 0;
		batAbsOrd = 0;
		bwlAbsOrd = 0;
		
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
		    String strLine;
		    int lineNum = 1;

		    Element currentPs = null;
			int psOrdinal = 0;
			int psRuns = 0;
			String prevBat = null;
			
			while ((strLine = br.readLine()) != null)   
			{
				if(lineNum == 1)
				{
					elem.setAttribute("seasonId", getValue(strLine));
					activeSeason = Integer.parseInt(getValue(strLine));
				}
				else if(lineNum == 2)
				{
					elem.setAttribute("round", getValue(strLine));
				}
				else if(lineNum == 3)
				{
					elem.setAttribute("date", getValue(strLine));
				}
				else if(lineNum == 4)
				{
					elem.setAttribute("time", getValue(strLine));
				}
				else if(lineNum == 5)
				{
					elem.setAttribute("us", getValue(strLine));
				}
				else if(lineNum == 6)
				{
					elem.setAttribute("opposition", getValue(strLine));
				}
				else if(lineNum >= 8 && lineNum <= 15)
				{
					if(strLine.trim().length() > 0)
					{
						if(lineNum % 2 == 0 || currentPs == null)
						{
							psOrdinal ++;
							currentPs = createPartnership(doc, psOrdinal);
							elem.appendChild(currentPs);
							currentPs.setAttribute("id", String.valueOf(partnershipId ++));
							prevBat = strLine.split("\t")[0];
						}
						
						Element inns = convertInnings(doc, strLine);
						inns.setAttribute("id", String.valueOf(inningsId ++));
						currentPs.appendChild(inns);
						psRuns += runsOffInnings(strLine);
						
						if(lineNum % 2 == 1)
						{
							currentPs.setAttribute("score", String.valueOf(psRuns));
							psRuns = 0;
							
							String b1 = prevBat.trim().toLowerCase();
							String b2 = strLine.split("\t")[0].trim().toLowerCase();
							
							String p1 = b1.compareTo(b2) > 0 ? b2 : b1;
							String p2 = b1.compareTo(b2) > 0 ? b1 : b2;
							
							currentPs.setAttribute("player1", p1);
							currentPs.setAttribute("player2", p2);
						}
					}
				}
				else if(lineNum >= 16 && lineNum <= 31)
				{
					if(strLine.trim().length() > 0)
					{
						elem.appendChild(convertOver(doc, lineNum - 15, strLine));
					}
				}
				
				lineNum ++;
			}

			//Close the input stream
			in.close();
			
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
		
		return elem;
	}
	
	private Element createPartnership(Document doc, int ordinal)
	{
		Element elem = doc.createElement("partnership");
		
		elem.setAttribute("ordinal", String.valueOf(ordinal));
		
		return elem;
	}
	
	private Element convertInnings(Document doc, String str)
	{
		Element node = doc.createElement("innings");
		
		convertBalls(doc, node, str);
		
		return node;
	}
	
	private Node convertOver(Document doc, int ordinal, String str)
	{
		Element node = doc.createElement("over");
		
		convertBalls(doc, node, str);
		node.setAttribute("ordinal", String.valueOf(ordinal));
		node.setAttribute("id", String.valueOf(overId ++));

		return node;
	}
	
	private void convertBalls(Document doc, Element container, String ballCollection)
	{
		String[] split = ballCollection.split("\t");
		
		int score = 0;
		int count = 0;
		
		for(int i=0; i<split.length; i++)
		{
			if(i > 0)
			{
				Element ball = convertBall(doc, i, split[i].toUpperCase());
				
				if(ball != null)
				{
					container.appendChild(ball);
					score += runsOffBall(split[i].toUpperCase());
					count ++;
				}
			}
			else
			{
				container.setAttribute("player", split[0].toLowerCase());
				players.add(split[0].toLowerCase());
				
				if(activeSeason >= currentSeason)
				{
					currentPlayers.add(split[0].toLowerCase());
				}
			}
		}
		
		container.setAttribute("score", String.valueOf(score));
		container.setAttribute("balls", String.valueOf(count));
	}
	
	private Element convertBall(Document doc, int ordinal, String oldBall)
	{
		if(oldBall == null || oldBall.trim().equals(""))
		{
			return null;
		}

		String newBall = oldBall.replace("ls", "W");		
		newBall = oldBall.replace("LS", "W");		
		
		Element elem = doc.createElement("ball");
		
		elem.setAttribute("ordinal", String.valueOf(ordinal));
		elem.setAttribute("score", newBall);
		elem.setAttribute("runs", String.valueOf(runsOffBall(newBall)));
//		elem.setAttribute("absOrdinal", String.valueOf(++batAbsOrd));
//		batCum += runsOffBall(ball);
//		elem.setAttribute("cumScore", String.valueOf(batCum));
		
		return elem;
	}
	
	private int runsOffInnings(String inns)
	{
		String[] split = inns.split("\t");
		
		int score = 0;
		for(int i=1; i<split.length; i++)
		{
			score += runsOffBall(split[i]);
		}
		
		return score;
	}
	
	private int runsOffBall(String ball)
	{
		ball = ball.toLowerCase();
		ball = ball.replace("ls", "w");		

		if(ball == null || ball.length() == 0)
		{
			return 0;
		}
		else if(ball.equals("w") || ball.equals("nb"))
		{
			return 2;
		}
		else if(ball.equals("w1") || ball.equals("nb1"))
		{
			return 3;
		}
		else if(ball.equals("w2") || ball.equals("nb2"))
		{
			return 4;
		}
		else if(ball.equals("w3") || ball.equals("nb3"))
		{
			return 5;
		}
		else if(ball.equals("w4") || ball.equals("nb4"))
		{
			return 6;
		}
		else if(ball.equals("w5") || ball.equals("nb5"))
		{
			return 7;
		}
		else if(ball.equals("w7") || ball.equals("nb7"))
		{
			return 9;
		}
		else if(ball.equals("r") || ball.equals("m") || ball.equals("c") || ball.equals("s") || ball.equals("b") || ball.equals("l") || ball.equals("hw"))
		{
			return -5;
		}
		else if(ball.length() > 0 && Character.isDigit(ball.charAt(0)))
		{
			return Integer.parseInt(ball);
		}
		else
		{
			System.err.println("illegal ball: [" + ball + "]");
			return 999999;
		}
	}
	
	private String getValue(String str)
	{
		String[] split = str.split("=");
		return split[1].trim();
	}
}
