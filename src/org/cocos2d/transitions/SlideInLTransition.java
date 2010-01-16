package org.cocos2d.transitions;

import org.cocos2d.actions.ease.EaseOut;
import org.cocos2d.actions.instant.CallFunc;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.types.CCSize;

/**
 * SlideInL Transition.
 * Slide in the incoming scene from the left border.
 */
public class SlideInLTransition extends TransitionScene {

    public SlideInLTransition(float t, Scene s) {
        super(t, s);
    }

    public void onEnter() {
        super.onEnter();

        initScenes();

        IntervalAction in = action();
        IntervalAction out = in.copy();

        inScene.runAction(EaseOut.action(in, 2.0f));
        outScene.runAction(Sequence.actions(
                EaseOut.action(out, 2.0f),
                CallFunc.action(this, "finish")));
    }

    /**
     * returns the action that will be performed
     */
    protected IntervalAction action() {
        CCSize s = Director.sharedDirector().winSize();
        return MoveTo.action(duration, s.width, 0);
    }

    /**
     * initializes the scenes
     */
    protected void initScenes() {
        CCSize s = Director.sharedDirector().winSize();
        inScene.setPosition(-s.width, 0);
    }

}
