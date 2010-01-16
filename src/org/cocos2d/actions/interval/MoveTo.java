package org.cocos2d.actions.interval;

import android.graphics.PointF;
import org.cocos2d.nodes.CocosNode;

//
// MoveTo
//

public class MoveTo extends IntervalAction {
    private PointF endPosition;
    private PointF startPosition;
    protected PointF delta;

    public static MoveTo action(float t, float x, float y) {
        return new MoveTo(t, x, y);
    }

    protected MoveTo(float t, float x, float y) {
        super(t);
        startPosition = new PointF();
        endPosition = new PointF(x, y);
        delta = new PointF();
    }

    @Override
    public IntervalAction copy() {
        return new MoveTo(duration, endPosition.x, endPosition.y);
    }

    long now = 0L;

    @Override
    public void start(CocosNode aTarget) {
        super.start(aTarget);

        startPosition.x = target.getPositionX();
        startPosition.y = target.getPositionY();
        if (endPosition != null) {
            delta.x = endPosition.x - startPosition.x;
            delta.y = endPosition.y - startPosition.y;
        }
        now = System.currentTimeMillis();
    }

    @Override
    public void update(float t) {
        target.setPosition(startPosition.x + delta.x * t, startPosition.y + delta.y * t);
    }
}
