package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.types.ccTime;

public class RepeatForever extends Action {
	
	public static RepeatForever actionWithAction(IntervalAction action){
		return new RepeatForever();
	}
	
	IntervalAction other;
	
	public RepeatForever initWithAction(IntervalAction action){
		other = action;
		return this;
	}
	
	public void start(){
		super.start();
		other.target = this.target;
		other.start();
	}
	
	public void step(ccTime dt){
		other.step(dt);
		if(other.isDone())
			other.start();
	}
	
	public boolean isDone(){
		return false;
	}
	
	public RepeatForever reverse(){
		return RepeatForever.actionWithAction(other.reverse());
	}
}
