package com.moandroid.cocos2d.action;

import java.util.ArrayList;
import java.util.List;

import com.moandroid.cocos2d.nodes.CocosNode;
import com.moandroid.cocos2d.types.ccTime;

// This class does not look like in cocos2d-iphone project because there is no sense here to "translate word by word in Java"
public class Sequence extends IntervalAction {	
	ccTime split;
	int last;
	private List<FiniteTimeAction> actions;

	public Sequence(FiniteTimeAction one, FiniteTimeAction two) {
		if(one == null || two == null)
			throw new IllegalArgumentException("Only not null actions could added to a sequence");
		List<FiniteTimeAction> convertedInList = new ArrayList<FiniteTimeAction>(2);
		convertedInList.add(one);
		convertedInList.add(two);
		setActions(convertedInList);
	}
	
	public Sequence(List<FiniteTimeAction> actions) {
		setActions(actions);
	}

	@Override
	public void start(CocosNode target){
		super.start(target);
		split = new ccTime(actions.get(0).getDuration().time / getDuration().time);
		last = -1;
	}
	
	@Override
	public void stop(){
		for(FiniteTimeAction action : actions)
			action.stop();
		super.stop();
	}
	
	@Override
	public Object reverse() {
		List<FiniteTimeAction> reversedList = new ArrayList<FiniteTimeAction>(actions.size());
		for(int i = actions.size(); i >= 0; i--)
			reversedList.add((FiniteTimeAction)actions.get(i).reverse());
		return new Sequence(reversedList);
	}

	@Override
	public void update(ccTime time) {
		//TODO: Rename parameter or internal variables to avoid time.time
		int found = 0;
		ccTime newTime = new ccTime(0);
		if(time.time >= split.time){
			found = 1;
			if(split.time == 1)
				newTime.time = 1;
			else
				newTime.time = (time.time - split.time) / (1 - split.time);
		} else {
			found = 0;
			if(split.time != 0)
				newTime.time = time.time / split.time;
			else
				newTime.time = 1;
		}
		
		if (last == -1 && found == 1)	{
			actions.get(0).start(target);
			actions.get(0).update(new ccTime(1));
			actions.get(0).stop();
		}

		if (last != found ) {
			if( last != -1 ) {
				FiniteTimeAction concernedAction = actions.get(found);
				concernedAction.update(new ccTime(1));
				concernedAction.stop();
			} 
			actions.get(found).start(target);
		}
		actions.get(found).update(newTime);
		last = found;
	}

	protected void setActions(List<FiniteTimeAction> actions) {
		this.actions = actions;
	}

	protected List<FiniteTimeAction> getActions() {
		if(actions == null || actions.isEmpty())
			throw new IllegalArgumentException("Only not null and not empty actions lists could added to a sequence");
		ccTime totalTime = new ccTime(0);
		for(FiniteTimeAction action : actions){
			if(action == null)
				throw new IllegalArgumentException("List of actions can not contain a null action");
			totalTime.time += action.getDuration().time;
		}
		setDuration(totalTime);
		return actions;
	}

}
