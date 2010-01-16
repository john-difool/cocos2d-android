package org.cocos2d.transitions;

import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.types.CCSize;

/**
 * SlideInR Transition.
 * Slide in the incoming scene from the right border.
 */
public class SlideInRTransition extends SlideInLTransition {

    public SlideInRTransition(float t, Scene s) {
        super(t, s);
    }

    /**
     * initializes the scenes
     */
    protected void initScenes() {
        CCSize s = Director.sharedDirector().winSize();
        inScene.setPosition(-s.width, 0);
    }
}
