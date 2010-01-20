package org.cocos2d.layers;

import android.view.MotionEvent;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.events.TouchDispatcher;
import org.cocos2d.events.TouchDelegate;
import org.cocos2d.types.CCSize;

public class Layer extends CocosNode implements TouchDelegate {

    protected boolean isTouchEnabled_;

    //! whether or not it will receive Accelerometer events
    protected boolean isAccelerometerEnabled_;

    public boolean isTouchEnabled()
    {
        return isTouchEnabled_;
    }

    public void setIsTouchEnabled(boolean enabled)
    {
        if( isTouchEnabled_ != enabled ) {
            isTouchEnabled_ = enabled;
            if( isRunning() ) {
                if( enabled )
                    registerWithTouchDispatcher();
                else
                    TouchDispatcher.sharedDispatcher().removeDelegate(this);
            }
        }
    }

    public static Layer node() {
        return new Layer();
    }

    protected Layer() {
        CCSize s = Director.sharedDirector().winSize();
        setRelativeAnchorPoint(false);

        setAnchorPoint(0.5f, 0.5f);
        setContentSize(s.width, s.height);
        setRelativeAnchorPoint(false);

        isTouchEnabled_ = false;
        isAccelerometerEnabled_ = false;
            
    }

    protected void registerWithTouchDispatcher() {
        TouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }

    @Override
    public void onEnter() {

        // register 'parent' nodes first
        // since events are propagated in reverse order
        if (isTouchEnabled_)
            registerWithTouchDispatcher();

        // then iterate over all the children
        super.onEnter();

//        if( isAccelerometerEnabled )
//            [[UIAccelerometer sharedAccelerometer] setDelegate:self];
    }

    @Override
    public void onExit() {

        if (isTouchEnabled_)
            TouchDispatcher.sharedDispatcher().removeDelegate(this);

//        if( isAccelerometerEnabled )
//            [[UIAccelerometer sharedAccelerometer] setDelegate:nil];

        super.onExit();
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        return TouchDispatcher.kEventHandled;  // TODO Auto-generated method stub
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        return TouchDispatcher.kEventIgnored;  // TODO Auto-generated method stub
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        return TouchDispatcher.kEventIgnored;  // TODO Auto-generated method stub
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        return TouchDispatcher.kEventIgnored;  // TODO Auto-generated method stub
    }
}
