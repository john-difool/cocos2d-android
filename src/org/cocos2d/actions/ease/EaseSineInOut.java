package org.cocos2d.actions.ease;

import org.cocos2d.actions.interval.IntervalAction;

public class EaseSineInOut extends EaseAction {

    public static EaseSineInOut action(IntervalAction action) {
        return new EaseSineInOut(action);
    }

    protected EaseSineInOut(IntervalAction action) {
        super(action);
    }

	@Override
    public EaseAction copy() {
        return new EaseSineInOut(other.copy());
    }

    @Override
    public void update(float t) {
        other.update(-0.5f * ((float)Math.cos(Math.PI * t) - 1));
    }
}


