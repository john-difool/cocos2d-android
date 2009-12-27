package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.types.ccTime;

public class RepeatForever extends IntervalAction {
	
	IntervalAction other;
	
	public RepeatForever(IntervalAction action){
		other = action;
	}
		
	public void start(){
		super.start(this.target);
		other.target = this.target;
		other.start(target);
	}
	
	public void step(ccTime dt){
		other.step(dt);
		if(other.isDone())
			other.start(target);
	}
	
	public boolean isDone(){
		return false;
	}
	
	public RepeatForever reverse(){
		return new RepeatForever((IntervalAction)other.reverse());
	}

	@Override
	public void update(ccTime time) {
		// TODO Auto-generated method stub
		
	}
}
