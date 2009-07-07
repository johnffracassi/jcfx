package com.siebentag.cj.game.event;

import com.siebentag.cj.model.Ball;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("Scorer")
@Consumer("ScoreRecorder")
public class ScoringEvent extends AbstractEvent
{
	Ball ball;

	public ScoringEvent(Ball ball)
	{
		this.ball = ball;
	}

	public Ball getBall()
	{
		return ball;
	}

	public void setBall(Ball ball)
	{
		this.ball = ball;
	}

	public String getDescription()
	{
		return "Scoring event (" + ball.getRunsForBatsman() + ")";
	}

	@Override
    public void cancel()
    {
    }
}
