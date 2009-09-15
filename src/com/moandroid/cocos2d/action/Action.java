package com.moandroid.cocos2d.action;

import com.moandroid.cocos2d.nodes.CocosNode;
import com.moandroid.cocos2d.types.ccTime;

public class Action {
	
	public static Action action(){
		return new Action();
	}
	
	public CocosNode target;
	public ActionTag tag;
	
	public Action(){
		target = null;
		tag = ActionTag.kActionTagInvalid;
	}
	
	public void start(){
		
	}
	
	public boolean isDone(){
		return true;
	}
	
	public void stop(){
	
	}
	
	public void step(ccTime dt){
		
	}
	
	public void update(ccTime time){
	}
}
