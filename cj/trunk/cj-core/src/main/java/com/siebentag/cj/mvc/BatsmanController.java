package com.siebentag.cj.mvc;

import com.siebentag.cj.model.Player;



public interface BatsmanController extends PlayerController
{
	void setState(Player player, BatsmanState state, double time);
	BatsmanState getState(Player player);
	
	/**
	 * Set a single batsman's role
	 */
	void setBatsman(Player player, BatsmanRole role);
	
	/**
	 * Get a batsman (keyed by role)
	 */
	Player getBatsman(BatsmanRole role);
	
	/**
	 * Add a run to the queue
	 */
	void queueRun(double time);
	
	/**
	 * Dequeue all runs, send batsmen back to nearest crease
	 */
	void cancelQueuedRuns();
}