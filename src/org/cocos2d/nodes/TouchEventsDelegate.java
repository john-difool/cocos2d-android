package org.cocos2d.nodes;

import android.view.MotionEvent;

public interface TouchEventsDelegate {
    public boolean CCTouchesBegan(MotionEvent event);

    public void CCTouchesMoved(MotionEvent event);

    public void CCTouchesEnded(MotionEvent event);

    public void CCTouchesCancelled(MotionEvent event);
}
