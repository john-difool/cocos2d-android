package org.cocos2d.actions.ease;

import android.util.FloatMath;
import org.cocos2d.actions.interval.IntervalAction;

public class EaseSineInOut extends EaseAction {

    public static EaseSineInOut action(IntervalAction action) {
        return new EaseSineInOut(action);
    }

    protected EaseSineInOut(IntervalAction action) {
        super(action);
    }

    @Override
    public void update(float t) {
        other.update(-0.5f * (FloatMath.cos((float) Math.PI * t) - 1));
    }

    @Override
    public IntervalAction reverse() {
        return new EaseExponentialOut(other.reverse());
    }

}


