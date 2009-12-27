package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.types.ccTime;

public abstract class FiniteTimeAction extends Action {
	private ccTime duration;
	
	public FiniteTimeAction(){
		this.setDuration(new ccTime(0));
	}
	
	public FiniteTimeAction(ccTime duration){
		this.setDuration(duration);
	}
	
	public abstract Object reverse();

	public void setDuration(ccTime duration) {
		this.duration = duration;
	}

	public ccTime getDuration() {
		return duration;
	}
}
