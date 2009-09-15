package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.types.ccTime;

public class Speed extends Action {
	public static Speed actionWithAction(IntervalAction action, float speedRate){
		Speed sp = new Speed();
		sp.initWithAction(action, speedRate);
		return sp;
	}	
	
	IntervalAction other;
	public float speed;
	
	public Speed initWithAction(IntervalAction action, float speedRate){
		other = action;
		speed = speedRate;
		return this;
	}
	
	public void start(){
		super.start();
		other.target = target;
		other.start();
	}
	
	public void step(ccTime dt){
		other.step(dt.multiply (speed));
	}
	
	public boolean isDone(){
		return other.isDone();
	}
	
	public Speed reverse(){
		return Speed.actionWithAction(other.reverse(), speed);
	}
}
