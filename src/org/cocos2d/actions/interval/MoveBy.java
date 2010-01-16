package org.cocos2d.actions.interval;

import android.graphics.PointF;
import org.cocos2d.nodes.CocosNode;

//
// MoveBy
//

public class MoveBy extends MoveTo {
    private PointF saved;

    public static MoveBy action(float t, float x, float y) {
        return new MoveBy(t, x, y);
    }

    protected MoveBy(float t, float x, float y) {
        super(t, x, y);
        delta = new PointF(x, y);
        saved = new PointF();
    }

    @Override
    public IntervalAction copy() {
        return new MoveBy(duration, delta.x, delta.y);
    }

    @Override
    public void start(CocosNode aTarget) {
        saved.x = delta.x;
        saved.y = delta.y;
        super.start(aTarget);
        delta.x = saved.x;
        delta.y = saved.y;
    }

    @Override
    public IntervalAction reverse() {
        return new MoveBy(duration, -delta.x, -delta.y);
    }
}
