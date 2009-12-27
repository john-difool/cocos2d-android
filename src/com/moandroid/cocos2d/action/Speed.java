package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.types.ccTime;

public class Speed extends Action {
	IntervalAction other;
	public float speed;
	
	public Speed(IntervalAction action, float speedRate){
		other = action;
		speed = speedRate;
	}
	
	public void start(){
		super.start(this.target);
		other.target = target;
		other.start(target);
	}
	
	public void step(ccTime dt){
		other.step(dt.multiply (speed));
	}
	
	public boolean isDone(){
		return other.isDone();
	}
	
	public Speed reverse(){
		return new Speed((IntervalAction)other.reverse(), speed);
	}

	@Override
	public void update(ccTime time) {
		// TODO: Nothing to do?
	}
}
