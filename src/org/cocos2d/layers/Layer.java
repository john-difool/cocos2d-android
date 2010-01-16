package org.cocos2d.layers;

import android.view.MotionEvent;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.TouchEventsDelegate;
import org.cocos2d.types.CCSize;

public class Layer extends CocosNode implements TouchEventsDelegate {
    public boolean isTouchEnabled;

    //! whether or not it will receive Accelerometer events
    public boolean isAccelerometerEnabled;


    public static Layer layer() {
        return new Layer();
    }

    protected Layer() {
        CCSize s = Director.sharedDirector().winSize();
        setRelativeAnchorPoint(false);

        setTransformAnchor(s.width / 2, s.height / 2);

        isTouchEnabled = false;
        isAccelerometerEnabled = false;

    }

    @Override
    public void onEnter() {

        // register 'parent' nodes first
        // since events are propagated in reverse order
        if (isTouchEnabled)
            Director.sharedDirector().addEventHandler(this);

        // the iterate over all the children
        super.onEnter();

//        if( isAccelerometerEnabled )
//            [[UIAccelerometer sharedAccelerometer] setDelegate:self];
    }

    @Override
    public void onExit() {
        if (isTouchEnabled)
            Director.sharedDirector().removeEventHandler(this);

//        if( isAccelerometerEnabled )
//            [[UIAccelerometer sharedAccelerometer] setDelegate:nil];

        super.onExit();
    }

    public boolean CCTouchesBegan(MotionEvent event) {
        return Director.kEventIgnored;  // TODO Auto-generated method stub
    }

    public void CCTouchesMoved(MotionEvent event) {
    }

    public void CCTouchesEnded(MotionEvent event) {
    }

    public void CCTouchesCancelled(MotionEvent event) {
    }
}
