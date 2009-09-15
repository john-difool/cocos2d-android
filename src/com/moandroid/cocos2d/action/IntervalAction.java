package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.types.ccTime;

public class IntervalAction extends FiniteTimeAction {
	
	public static IntervalAction actionWithDuration(ccTime dt){
		return null;
	}
	
	public ccTime elapsed;
	long lastUpdate;
	
	public IntervalAction(){
		duration = new ccTime(0);
	}
	
	public IntervalAction initWithDuration(ccTime d){
		return null;
	}
	
	public void start(){
		
	}
	
	public boolean isDone(){
		return true;
	}
	
	public IntervalAction reverse(){
		return IntervalAction.actionWithDuration(this.duration);
	}
	
	public void step(ccTime dt){
		update(new ccTime(1));
	}
	
	public void update(ccTime t){
		
	}
}
