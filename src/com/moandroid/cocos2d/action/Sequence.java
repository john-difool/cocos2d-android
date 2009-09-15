package com.moandroid.cocos2d.action;

import java.util.ArrayList;

import com.moandroid.cocos2d.types.ccTime;

public class Sequence extends IntervalAction {
	
	public static Action actions(	FiniteTimeAction actionOne,
									FiniteTimeAction actionTwo){
		return null;
	}
	public static Action actionOne( FiniteTimeAction actionOne,
									FiniteTimeAction actionTwo){
		Sequence newSeq = new Sequence();
		newSeq.initOne(actionOne, actionTwo);
		return newSeq;
	}
	
	ccTime split;
	int last;
	ArrayList<Action> actions;
	public Action initOne(  FiniteTimeAction actionOne,
							FiniteTimeAction actionTwo){
		return null;
	}
	
	public void start(){
	}
	
	public void update(ccTime t){
		
	}
	
	public IntervalAction reverse(){
		return null;
	}
}
