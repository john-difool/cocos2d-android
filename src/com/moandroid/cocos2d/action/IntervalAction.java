package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.nodes.CocosNode;
import com.moandroid.cocos2d.types.ccTime;

public abstract class IntervalAction extends FiniteTimeAction {
	
	public ccTime elapsed;
	boolean firstTick;
	
	public IntervalAction(){
		elapsed = new ccTime(0);
		firstTick = true;
	}
	
	public IntervalAction(ccTime duration){
		setDuration(duration);
		elapsed = new ccTime(0);
		firstTick = true;
	}
	
	public void start(CocosNode target){
		super.start(target);
		elapsed = new ccTime(0);
		firstTick = true;
	}
	
	public boolean isDone(){
		return (elapsed.time >= getDuration().time);
	}

	@Override
	public void step(ccTime dt) {
		if( firstTick ) {
			firstTick = false;
			elapsed.time = 0;
		} else
			elapsed.time += dt.time;
		update(new ccTime(Math.min(1, elapsed.time / getDuration().time)));
	}

	@Override
	public void setDuration(ccTime duration) {
		if(getDuration().time == 0)
			setDuration(new ccTime(0.00000001f));
		else
			super.setDuration(duration);
	}
}
