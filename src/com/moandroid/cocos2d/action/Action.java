package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.nodes.CocosNode;
import com.moandroid.cocos2d.types.ccTime;

public abstract class Action {
	
	public CocosNode target;
	public ActionTag tag;
	
	public Action(){
		target = null;
		tag = ActionTag.kActionTagInvalid;
	}
	
	public void start(CocosNode target){
		this.target = target; 
	}
	
	public boolean isDone(){
		return true;
	}
	
	public void stop(){
		target = null;
	}
	
	public abstract void step(ccTime dt);
	
	public abstract void update(ccTime time);
}
