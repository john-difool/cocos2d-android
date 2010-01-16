package org.cocos2d.actions.ease;

import android.util.FloatMath;
import org.cocos2d.actions.interval.IntervalAction;

public class EaseSinOut extends EaseAction {

    public static EaseSinOut action(IntervalAction action) {
        return new EaseSinOut(action);
    }

    protected EaseSinOut(IntervalAction action) {
        super(action);
    }

    @Override
    public void update(float t) {
        other.update(FloatMath.sin(t * (float) Math.PI / 2));
    }

    @Override
    public IntervalAction reverse() {
        return new EaseExponentialOut(other.reverse());
    }

}

