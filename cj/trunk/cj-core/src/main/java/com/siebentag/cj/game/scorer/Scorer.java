package com.siebentag.cj.game.scorer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.siebentag.cj.game.event.ScoringEvent;
import com.siebentag.cj.model.Ball;
import com.siebentag.cj.model.BatsmanInnings;
import com.siebentag.cj.model.BatsmanInningsCollection;
import com.siebentag.cj.model.BowlerInnings;
import com.siebentag.cj.model.BowlerInningsCollection;
import com.siebentag.cj.model.Match;
import com.siebentag.cj.model.MatchInnings;
import com.siebentag.cj.model.Over;
import com.siebentag.cj.model.OverCollection;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.mvc.MatchFormat;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;

@Component
public class Scorer implements EventListener
{
	// match scoped
	private Match match;
	private MatchInnings innings;
	private MatchFormat format;

	// innings scoped
	private BatsmanInningsCollection batsmanInningsCollection;
	private Map<Player,BatsmanInnings> batsmanInningsMap;
	private BowlerInningsCollection bowlerInningsCollection;
	private Map<Player,BowlerInnings> bowlerInningsMap;
	
	private Player currentBowler;
	private Over currentOver;
	private List<Over> overs;
	
	public void startMatch(MatchFormat format, Team home, Team away)
	{
		match = new Match();
		match.setHomeTeam(home);
		match.setAwayTeam(away);

		this.format = format;
		
		startInnings();
	}
	
	public void startInnings()
	{
		innings = new MatchInnings();
		innings.setOrdinal(match.getInnings().size() + 1);

		batsmanInningsCollection = new BatsmanInningsCollection();
		innings.setBatting(batsmanInningsCollection);
		
		bowlerInningsCollection = new BowlerInningsCollection();
		innings.setBowling(bowlerInningsCollection);
		
		match.getInnings().add(innings);
	}
	
	public void addBall(Ball ball)
	{
		Player batsman = ball.getStriker();
		Player bowler = ball.getBowler();
		
		addBallToBatsman(batsman, ball);
		addBallToBowler(bowler, ball);
	}
	
	private void addBallToBatsman(Player batsman, Ball ball)
	{
		BatsmanInnings inns = getBatsmanInnings(batsman);
		inns.getBalls().getBalls().add(ball);
	}
	
	private void addBallToBowler(Player bowler, Ball ball)
	{
		BowlerInnings inns = getBowlerInnings(bowler);
		
		// check we still have the same bowler
		if(!bowler.equals(currentBowler))
		{
			currentBowler = bowler;
			currentOver = null;
		}
		
		// new over
		if(currentOver == null)
		{
			currentOver = new Over();
			currentOver.setOrdinal(overs.size() + 1);
			
			overs.add(currentOver);
			
			addOverToInnings(inns, currentOver);
		}
	}

	private BatsmanInnings getBatsmanInnings(Player batsman)
    {
	    if(batsmanInningsMap.containsKey(batsman))
	    {
	    	return batsmanInningsMap.get(batsman);
	    }
	    else
	    {
	    	BatsmanInnings inns = new BatsmanInnings();
	    	inns.setPlayer(batsman);
	    	inns.setOrdinal(batsmanInningsCollection.getInnings().size() + 1);
	    	batsmanInningsMap.put(batsman, inns);
	    	batsmanInningsCollection.getInnings().add(inns);
	    	return inns;
	    }
    }

	private BowlerInnings getBowlerInnings(Player bowler)
    {
	    if(bowlerInningsMap.containsKey(bowler))
	    {
	    	return bowlerInningsMap.get(bowler);
	    }
	    else
	    {
	    	BowlerInnings inns = new BowlerInnings();
	    	inns.setPlayer(bowler);
	    	inns.setOrdinal(bowlerInningsCollection.getInnings().size() + 1);
	    	bowlerInningsMap.put(bowler, inns);
	    	bowlerInningsCollection.getInnings().add(inns);
	    	return inns;
	    }
    }

	private void addOverToInnings(BowlerInnings inns, Over currentOver)
    {
		if(inns.getOvers() == null)
		{
			OverCollection oc = new OverCollection();
			oc.getOvers().add(currentOver);
			inns.setOvers(oc);
		}
		else
		{
			inns.getOvers().getOvers().add(currentOver);
		}
    }

	public void event(Event event)
    {
		if(event instanceof ScoringEvent)
		{
			addBall(((ScoringEvent)event).getBall());
		}
    }

	public Class<?>[] register()
    {
	    return new Class<?>[] {
	    	ScoringEvent.class	
	    };
    }

	public MatchFormat getFormat()
    {
    	return format;
    }

	public void setFormat(MatchFormat format)
    {
    	this.format = format;
    }
}
