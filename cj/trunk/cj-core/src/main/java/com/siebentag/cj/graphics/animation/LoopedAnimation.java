package com.siebentag.cj.graphics.animation;


public class LoopedAnimation extends OneShotAnimation
{
	public int getFrameIndexByTime(double time)
	{
		assert getFrameCount() > 0 : "No frames for animation " + getName();
		assert time > 0 : "time has not advanced yet (" + time + ")";
		assert getTotalTime() > 0 : "looped animation has total time of " + getTotalTime();

		// requesting a frame past the end?
		if(time > getTotalTime())
		{
			time = time % getTotalTime();
		}
		
		return super.getFrameIndexByTime(time);
	}
}
