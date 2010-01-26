package org.cocos2d.actions.ease;

import android.util.FloatMath;
import org.cocos2d.actions.interval.IntervalAction;

public class EaseSineIn extends EaseAction {

    public static EaseSineIn action(IntervalAction action) {
        return new EaseSineIn(action);
    }

    protected EaseSineIn(IntervalAction action) {
        super(action);
    }

    @Override
    public void update(float t) {
        other.update(-1 * FloatMath.cos(t * (float) Math.PI / 2) + 1);
    }

    @Override
    public IntervalAction reverse() {
        return new EaseExponentialOut(other.reverse());
    }


}

